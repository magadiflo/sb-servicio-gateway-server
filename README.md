# Sección 4: Spring Cloud Gateway

## Configuración del servicio Spring Cloud Gateway
```
spring:
  cloud:
    gateway:
      routes:
      - id: servicio-productos
        uri: lb://servicio-productos //lb, load balancer para poder acceder al recurso por lb 
        predicates:
          - Path=/api/productos/**
        filters:
          - StripPrefix=2 // 2 es por que el path (api/productos) está compuesta por 2 segmentos: api y productos  
      - id: servicio-item
        uri: lb://servicio-item //lb, load balancer para poder acceder al recurso por lb 
        predicates:
          - Path=/api/items/**
        filters:
          - StripPrefix=2 
```

# Implementando Filtros Globales

- Podemos implementar filtros globales, es decir filtros que se aplican a cualquier ruta.
- Para eso implementamos la interfaz **GlobalFilter** y le damos un orden.

````
@Component
public class EjemploGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) { ... }
    
    @Override
    public int getOrder() { ... }
}
````

# [Usando jjwt](https://github.com/jwtk/jjwt)

