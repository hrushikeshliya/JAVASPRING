package com.auth0.example;

import com.auth0.NonceUtils;
import com.auth0.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AppConfig appConfig;

    @Autowired
    public LoginController(final AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    protected String login(final Map<String, Object> model, final HttpServletRequest req) {
        logger.debug("Performing login");
        detectError(model);
        // add Nonce to storage
        NonceUtils.addNonceToStorage(req);
        model.put("clientId", "Y4mRQWwCk8s75s0VLIDOOTPF92SZlET4");
        model.put("domain", "eds.auth0.com");
        model.put("loginCallback", "/callback");
        model.put("state", SessionUtils.getState(req));
        model.put("connection", "Username-Password-Authentication");
        // for this sample only, this just allows configuration to
        // use Lock Widget or Auth0.js for login presentation
        // should only enable loginCustom for DB connection
        return "loginCustom";
    }

    private void detectError(final Map<String, Object> model) {
        if (model.get("error") != null) {
            model.put("error", true);
        } else {
            model.put("error", false);
        }
    }


}
