package ar.com.guanaco.diucon.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, ar.com.guanaco.diucon.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, ar.com.guanaco.diucon.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, ar.com.guanaco.diucon.domain.User.class.getName());
            createCache(cm, ar.com.guanaco.diucon.domain.Authority.class.getName());
            createCache(cm, ar.com.guanaco.diucon.domain.User.class.getName() + ".authorities");
            createCache(cm, ar.com.guanaco.diucon.domain.Categoria.class.getName());
            createCache(cm, ar.com.guanaco.diucon.domain.Categoria.class.getName() + ".subcategorias");
            createCache(cm, ar.com.guanaco.diucon.domain.SubCategoria.class.getName());
            createCache(cm, ar.com.guanaco.diucon.domain.SubCategoria.class.getName() + ".responsables");
            createCache(cm, ar.com.guanaco.diucon.domain.SubCategoria.class.getName() + ".plantillas");
            createCache(cm, ar.com.guanaco.diucon.domain.Incidente.class.getName());
            createCache(cm, ar.com.guanaco.diucon.domain.Incidente.class.getName() + ".comentarios");
            createCache(cm, ar.com.guanaco.diucon.domain.Incidente.class.getName() + ".historials");
            createCache(cm, ar.com.guanaco.diucon.domain.HistorialEstado.class.getName());
            createCache(cm, ar.com.guanaco.diucon.domain.Plantilla.class.getName());
            createCache(cm, ar.com.guanaco.diucon.domain.Plantilla.class.getName() + ".subcategorias");
            createCache(cm, ar.com.guanaco.diucon.domain.Comentario.class.getName());
            createCache(cm, ar.com.guanaco.diucon.domain.Responsable.class.getName());
            createCache(cm, ar.com.guanaco.diucon.domain.Responsable.class.getName() + ".subcategorias");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
