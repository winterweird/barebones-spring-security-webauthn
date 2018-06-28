var addCredential = function() {
    let challenge = loadChallenge();
    let userHandle = base64url.toBuffer(document.getElementById("userHandle").value);
    let makePublicKeyCredentialOptions = {
        rp: {
            name: "DIFI webauthn demo"
        },
        user: {
            id: userHandle,
            name: document.getElementById("emailAddress").value,
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

    navigator.credentials.create(credentialCreateOptions)
        .then(credential => {
            console.log(credential)
            // ...
        }).catch(err => console.log("Error:", err));
};
