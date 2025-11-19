package co.com.bancolombia.mongo.helper;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.mongo.ProductDBRepository;
import co.com.bancolombia.mongo.ProductRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class AdapterOperationsTest {

    @Mock
    private ProductDBRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    private ProductRepositoryAdapter adapter;

    private Product entity;
    private Flux<Product> entities;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        final Product product = Product.builder()
                .name("test")
                .build();

        when(objectMapper.map(any(), eq(Product.class)))
                .thenReturn(product);

        adapter = new ProductRepositoryAdapter(repository, objectMapper);

        entity = product;
        entities = Flux.just(entity);
    }

    @Test
    void testSave() {
        when(repository.save(entity)).thenReturn(Mono.just(entity));

        StepVerifier.create(adapter.save(entity))
                .expectNext(entity)
                .verifyComplete();
    }

    @Test
    void testSaveAll() {
        when(repository.saveAll(any(Flux.class))).thenReturn(entities);

        StepVerifier.create(adapter.saveAll(entities))
                .expectNext(entity)
                .verifyComplete();
    }

    @Test
    void testFindById() {
        when(repository.findById("key")).thenReturn(Mono.just(entity));

        StepVerifier.create(adapter.findById("key"))
                .expectNext(entity)
                .verifyComplete();
    }

    @Test
    void testFindByExample() {
        when(repository.findAll(any(Example.class))).thenReturn(entities);

        StepVerifier.create(adapter.findByExample(entity))
                .expectNext(entity)
                .verifyComplete();
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(entities);

        StepVerifier.create(adapter.findAll())
                .expectNext(entity)
                .verifyComplete();
    }

    @Test
    void testDeleteById() {
        when(repository.deleteById("key")).thenReturn(Mono.empty());

        StepVerifier.create(adapter.deleteById("key"))
                .verifyComplete();
    }
}
