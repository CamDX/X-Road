/**
 * The MIT License
 * Copyright (c) 2019- Nordic Institute for Interoperability Solutions (NIIS)
 * Copyright (c) 2018 Estonian Information System Authority (RIA),
 * Nordic Institute for Interoperability Solutions (NIIS), Population Register Centre (VRK)
 * Copyright (c) 2015-2017 Estonian Information System Authority (RIA), Population Register Centre (VRK)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ee.ria.xroad.proxy.conf;

import ee.ria.xroad.common.cert.CertChain;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * Authentication key manager used by the proxy.
 */
@Slf4j
public final class AuthKeyManager extends X509ExtendedKeyManager {

    private static final String ALIAS = "AuthKeyManager";

    private static final AuthKeyManager INSTANCE = new AuthKeyManager();

    /**
     * @return the authentication key manager instance
     */
    public static AuthKeyManager getInstance() {
        return INSTANCE;
    }

    private AuthKeyManager() {
    }

    @Override
    public String chooseClientAlias(String[] keyType, Principal[] issuers,
            Socket socket) {
        log.trace("chooseClientAlias {} {}", keyType, issuers);
        return ALIAS;
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers,
            Socket socket) {
        log.trace("chooseServerAlias {} {}", keyType, issuers);
        return ALIAS;
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        log.trace("getCertificateChain {}", alias);

        CertChain certChain = KeyConf.getAuthKey().getCertChain();
        List<X509Certificate> allCerts =
                certChain.getAllCertsWithoutTrustedRoot();
        return allCerts.toArray(new X509Certificate[allCerts.size()]);
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        log.trace("getClientAliases {} {}", keyType, issuers);
        return null;
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        log.trace("getPrivateKey {}", alias);
        return KeyConf.getAuthKey().getKey();
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        log.trace("getServerAliases {} {}", keyType, issuers);
        return null;
    }

    @Override
    public String chooseEngineClientAlias(String[] keyType, Principal[] issuers,
            SSLEngine engine) {
        log.trace("chooseEngineClientAlias {} {}", keyType, issuers);
        return ALIAS;
    }

    @Override
    public String chooseEngineServerAlias(String keyType, Principal[] issuers,
            SSLEngine engine) {
        log.trace("chooseEngineServerAlias {} {}", keyType, issuers);
        return ALIAS;
    }
}
