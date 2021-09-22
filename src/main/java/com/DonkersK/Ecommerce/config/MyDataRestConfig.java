package com.DonkersK.Ecommerce.config;

import com.DonkersK.Ecommerce.model.Country;
import com.DonkersK.Ecommerce.model.Product;
import com.DonkersK.Ecommerce.model.ProductCategory;
import com.DonkersK.Ecommerce.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import java.util.stream.Collectors;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    //leverages application.properties
    @Value("${allowed.origins}")
    private String[] AllowedOrigins;

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod [] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        //Disables HTTP methods for Product : PUT, POST, DELETE
        disableHttpMethods(Product.class, config, theUnsupportedActions);

        //Disables HTTP methods for ProductCategory : PUT, POST, DELETE
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);

        //Disables HTTP methods for State : PUT, POST, DELETE
        disableHttpMethods(State.class, config, theUnsupportedActions);

        //Disables HTTP methods for Country : PUT, POST, DELETE
        disableHttpMethods(Country.class, config, theUnsupportedActions);

        //helper method call
        exposeIds(config);

        //configure cors mapping , removes the need for @CrossOrigin("http://localhost:4200") annotation above repos
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(AllowedOrigins);
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure(((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions)))
                .withCollectionExposure(((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions)));
    }

    private void exposeIds(RepositoryRestConfiguration config){
        //Expose entity ids

/*        //Get list of all entity classes from entity manager
        Set<EntityType<?>> entities = entityManager. getMetamodel().getEntities();

        //create array of entity types
        List<Class> entityClasses = new ArrayList<>();

        //Get entity types for entities
        for (EntityType tempEntityType : entities){
            entityClasses.add(tempEntityType.getJavaType());
        }

        //Expose entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);*/

        //Does all the previous stuff, but Java 8
        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream()
                .map(e -> e.getJavaType()).collect(Collectors.toList()).toArray(new Class[0]));
    }
}
