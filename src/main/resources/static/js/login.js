var loadChallenge = function() {
    return base64url.toBuffer(document.getElementById("_challenge").value);
};

var getPublicKeyCredential = function() {
    if (typeof navigator.credentials === "undefined") {
        return Promise.reject("Credential management API not supported");
    }
    
    let challenge = loadChallenge();
    
    let publicKeyCredentialRequestOptions = {
        challenge: challenge,
        //timeout: null,
        //rpId: null,
        //allowCredentials: null,
        userVerification: "required",
        extensions:  {}
    };
    
    return navigator.credentials.get({
        mediation: "required",
        publicKey: publicKeyCredentialRequestOptions
    }).then(credential => {
        if (typeof credential === "undefined") {
            return Promise.reject("No credential is chosen");
        }
        else {
            return Promise.resolve(credential);
        }
    });
};

var attemptLogin = function() {
    getPublicKeyCredential()
        .then(res => console.log(res))
        .catch(err => console.log("Error:", err))
};

attemptLogin();
