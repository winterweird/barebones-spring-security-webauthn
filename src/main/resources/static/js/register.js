var createCredential = function() {
    let challenge = loadChallenge();
    let userHandle = base64url.toBuffer(document.getElementById("userHandle").value);
    let makePublicKeyCredentialOptions = {
        rp: {
            name: "DIFI webauthn demo"
        },
        user: {
            id: userHandle,
            name: document.getElementById("username").value,
            displayName: "user",
            icon: null
        },
        challenge: challenge,
        pubKeyCredParams: [
            {
                alg: -7,
                type: "public-key"
            }
        ],
        //timeout
        //excludeCredentials = []
        // NOTE: Not sure what this is for, so ignore it for the time being
        //authenticatorSelection: {
        //    requireResidentKey: document.findElementById("requireResidentKey").checked
        //}
        attestation: "none"
        //extensions
    };
    let credentialCreateOptions = {
        publicKey: makePublicKeyCredentialOptions
    };

    return navigator.credentials.create(credentialCreateOptions)
        .then(credential => {
            if (typeof credential === "undefined") {
                // I assume this has the same meaning as in the login thing
                return Promise.reject("No credential is chosen");
            }
            else {
                // ...
                return Promise.resolve(credential);
            }
        }).catch(err => console.log("Error:", err));
};

var attemptRegistration = function() {
    createCredential()
        .then(res => console.log(res))
        .catch(err => console.log("Error:", err));
};
