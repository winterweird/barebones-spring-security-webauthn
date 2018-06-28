/**
 * Find an input element with id "_challenge" and return its value. This element
 * should be provided by the server on relevant pages, and should consist of a
 * string of a certain number of random bytes encoded in base64 URL form.
 *
 * This function should not be used on pages which do not have such an element,
 * and requires the base64url module to also be included.
 */
var loadChallenge = function() {
    return base64url.toBuffer(document.getElementById("_challenge").value);
};
