package co.com.bancolombia.api.config;

import co.com.bancolombia.api.ProductApiRest;
import co.com.bancolombia.api.mapper.ProductMapper;
import co.com.bancolombia.api.route.API_REST_ROUTES;
import co.com.bancolombia.model.product.gateways.ProductRepository;
import co.com.bancolombia.usecase.product.ProductUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@ContextConfiguration(classes = {ProductApiRest.class})
@WebFluxTest
@Import({CorsConfig.class, SecurityHeadersConfig.class})
class ConfigTest {

    @MockitoBean
    ProductUseCase productUseCase;

    @MockitoBean
    ProductMapper productMapper;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void corsConfigurationShouldAllowOrigins() {
        webTestClient.patch()
                .uri(API_REST_ROUTES.API + API_REST_ROUTES.FRANCHISES
                        + API_REST_ROUTES.FRANCHISE_SLUG_PARAM + API_REST_ROUTES.BRANCHES
                        + API_REST_ROUTES.BRANCH_SLUG_PARAM + API_REST_ROUTES.PRODUCTS
                        + API_REST_ROUTES.SLUG_PARAM + API_REST_ROUTES.STOCK,
                        "example-franchise-slug", "example-branch-slug", "example-slug")
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().valueEquals("Content-Security-Policy",
                        "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
                .expectHeader().valueEquals("Server", "")
                .expectHeader().valueEquals("Cache-Control", "no-store")
                .expectHeader().valueEquals("Pragma", "no-cache")
                .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin");
    }

}
