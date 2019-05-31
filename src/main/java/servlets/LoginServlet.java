package servlets;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.secure.TokenChecked;
import data.DataBaseManager;
import model.User;
import responses.LoginResponse;
import responses.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.UUID;

public class LoginServlet extends HttpServlet {

    private final static int BACK_VK_APP_ID = 6978682;
    private final static String BACK_VK_SERVICE_TOKEN = "e136a993e136a993e136a9930ae15cd5e9ee136e136a993bde3e232acfd5751c6dbd0f0";

    private final static String BACK_GOOGLE_TOKEN = "478437036775-49u3nu9orrvv9i66ba1s27kv7j165ecs.apps.googleusercontent.com";

    private final static String SERVICE_VK = "vk";
    private final static String SERVICE_GOOGLE = "google";

    private Connection connection;


    @Override
    public void init() {
        connection = DataBaseManager.getManager().getConnection();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Api.ACTION_AUTH.equals(req.getParameter(Api.PARAMETER_ACTION))) {
            resp.getWriter().println(login(req));
        } else {
            super.doGet(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private String login(HttpServletRequest req) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        boolean singInResult = false;
        switch (req.getParameter(Api.PARAMETER_AUTH_METHOD)) {
            case SERVICE_VK:
                singInResult = singAsVkUser(req.getParameter(Api.PARAMETER_EXT_SERVICE_TOKEN), req.getParameter(Api.PARAMETER_EXT_SERVICE_ID));
                break;
            case SERVICE_GOOGLE:
                break;
        }
        if (singInResult) {
            //Ищем пользователя, если ок, генерим токен, иначе добавляем
            try {
                ResultSet resultSet = connection.createStatement().executeQuery("SELECT uuid FROM conference_maker.conference.users where vk_id = '" + req.getParameter(Api.PARAMETER_EXT_SERVICE_ID) + "'");
                if (resultSet.next()) {
                    UUID userUUID = UUID.fromString(resultSet.getString(1));
                    UUID userToken = UUID.randomUUID();
                    createUserToken(userUUID, userToken);
                    User user = DataBaseManager.getManager().getUser(userUUID);
                    return gson.toJson(new LoginResponse().setStatus(Response.STATUS_OK).setToken(UUID.randomUUID()).setUser(user));
                } else {
                    if (req.getParameter(Api.PARAMETER_AUTH_METHOD).equals(SERVICE_VK)) {
                        UUID userUUID = UUID.randomUUID();
                        UUID userToken = UUID.randomUUID();
                        connection.createStatement().execute("INSERT INTO conference_maker.conference.users VALUES ('" + userUUID + "', '" + req.getParameter(Api.PARAMETER_EXT_SERVICE_ID) + "', null, 'test', 'test', '123')");
                        createUserToken(userUUID, userToken);
                        return gson.toJson(new LoginResponse().setStatus(Response.STATUS_OK).setToken(UUID.randomUUID()));
                    }
                    return gson.toJson(new LoginResponse().setStatus(Response.STATUS_USER_NOT_FOUND));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return gson.toJson(new LoginResponse().setStatus(Response.STATUS_ERROR));
    }

    private boolean singAsGoogle(String token, String userId) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(token))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(userId);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            return false;
        }
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String user = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            // Use or store profile information
            // ...

        } else {
            System.out.println("Invalid ID token.");
            return false;
        }
        return true;
    }

    private void createUserToken(UUID userUUID, UUID userToken) throws SQLException {
        connection.createStatement().execute("INSERT INTO conference_maker.conference.tokens VALUES ('" + userUUID + "','" + userToken + "')");
    }


    private boolean singAsVkUser(String token, String userId) {
        try {
            TransportClient transportClient = HttpTransportClient.getInstance();
            VkApiClient vk = new VkApiClient(transportClient);
            TokenChecked tokenChecked = vk.secure().checkToken(new ServiceActor(BACK_VK_APP_ID, BACK_VK_SERVICE_TOKEN)).token(token).execute();
            return tokenChecked.getSuccess().getValue().equals("1") && tokenChecked.getUserId().toString().equals(userId);
        } catch (ClientException | ApiException ignored) {
            return false;
        }
    }
}
