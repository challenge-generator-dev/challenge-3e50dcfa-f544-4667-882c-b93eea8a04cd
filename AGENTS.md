# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Implementación de un servicio de autenticación en un sistema de banca digital**.

| | |
|---|---|
| Tema | Especialista en Framework con Proficiencia en Seguridad y Legibilidad de Código |
| Nivel | advanced-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean con capas separadas (dominio, aplicación, infraestructura) |
| Tiempo estimado | 10 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web n/a
- org.springframework.boot:spring-boot-starter-security n/a
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- io.jsonwebtoken:jjwt-api 0.11.5
- io.jsonwebtoken:jjwt-impl 0.11.5
- io.jsonwebtoken:jjwt-jackson 0.11.5
- org.postgresql:postgresql n/a
- org.springframework.boot:spring-boot-starter-test n/a
- org.springframework.security:spring-security-test n/a
- org.springdoc:springdoc-openapi-starter-webmvc-ui 2.5.0

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Exploración del marco de trabajo y requisitos de seguridad**: Un informe que detalle las funcionalidades del marco de trabajo y los estándares de seguridad identificados.
- **Fase 2 — Implementación del servicio de autenticación**: Un servicio de autenticación funcional que utiliza JWT y soporta múltiples mecanismos de autenticación.
- **Fase 3 — Evaluación y mejora del servicio de autenticación**: Un informe que detalle las mejoras realizadas en el servicio de autenticación y la justificación de las decisiones tomadas.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/main/java/com/bank/auth/domain/model/User.java` — El topic pide autenticacion/seguridad: este archivo es el ejercicio.
- [ ] `src/main/java/com/bank/auth/domain/port/UserRepository.java` — El topic pide autenticacion/seguridad: este archivo es el ejercicio.
- [ ] `src/main/java/com/bank/auth/domain/port/AuthServicePort.java` — El topic pide autenticacion/seguridad: este archivo es el ejercicio.
- [ ] `src/main/java/com/bank/auth/application/service/AuthService.java` — El topic pide autenticacion/seguridad: este archivo es el ejercicio.
- [ ] `src/main/java/com/bank/auth/application/dto/AuthRequest.java` — El topic pide autenticacion/seguridad: este archivo es el ejercicio.
- [ ] `src/main/java/com/bank/auth/application/dto/AuthResponse.java` — El topic pide autenticacion/seguridad: este archivo es el ejercicio.
- [ ] `src/main/java/com/bank/auth/infrastructure/adapter/UserJpaRepository.java` — El topic pide autenticacion/seguridad: este archivo es el ejercicio.
- [ ] `src/main/java/com/bank/auth/infrastructure/adapter/AuthController.java` — El topic pide autenticacion/seguridad: este archivo es el ejercicio.
- [ ] `src/main/java/com/bank/auth/infrastructure/security/JwtTokenUtil.java` — El topic pide autenticacion/seguridad: este archivo es el ejercicio.
- [ ] `src/main/java/com/bank/auth/infrastructure/security/SecurityConfig.java` — El topic pide autenticacion/seguridad: este archivo es el ejercicio.
- [ ] `src/main/java/com/bank/auth/infrastructure/security/JwtAuthenticationFilter.java` — El topic pide autenticacion/seguridad: este archivo es el ejercicio.
- [ ] `src/main/java/com/bank/auth/infrastructure/security/JwtAuthenticationEntryPoint.java` — El topic pide autenticacion/seguridad: este archivo es el ejercicio.
- [ ] `src/test/java/com/bank/auth/application/service/AuthServiceTest.java` — El topic pide autenticacion/seguridad: este archivo es el ejercicio.
- [ ] `src/test/java/com/bank/auth/infrastructure/adapter/AuthControllerTest.java` — El topic pide autenticacion/seguridad: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (3)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/bank/auth/infrastructure/adapter/AuthController.java` — `io.swagger.v3`
      El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/auth/infrastructure/security/JwtAuthenticationFilter.java` — `JwtTokenUtil.getUsernameFromToken`
      Se invoca `getUsernameFromToken` sobre `JwtTokenUtil`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/auth/application/service/AuthServiceTest.java` — `JwtTokenUtil.getUsernameFromToken`
      Se invoca `getUsernameFromToken` sobre `JwtTokenUtil`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (17)

- `pom.xml`
- `src/main/java/com/bank/auth/Application.java`
- `src/main/resources/application.yml`
- `src/main/java/com/bank/auth/domain/model/User.java`
- `src/main/java/com/bank/auth/domain/port/UserRepository.java`
- `src/main/java/com/bank/auth/domain/port/AuthServicePort.java`
- `src/main/java/com/bank/auth/application/dto/AuthRequest.java`
- `src/main/java/com/bank/auth/application/dto/AuthResponse.java`
- `src/main/java/com/bank/auth/infrastructure/adapter/UserJpaRepository.java`
- `src/main/java/com/bank/auth/application/service/AuthService.java`
- `src/main/java/com/bank/auth/infrastructure/adapter/AuthController.java`
- `src/main/java/com/bank/auth/infrastructure/security/JwtTokenUtil.java`
- `src/main/java/com/bank/auth/infrastructure/security/SecurityConfig.java`
- `src/main/java/com/bank/auth/infrastructure/security/JwtAuthenticationFilter.java`
- `src/main/java/com/bank/auth/infrastructure/security/JwtAuthenticationEntryPoint.java`
- `src/test/java/com/bank/auth/application/service/AuthServiceTest.java`
- `src/test/java/com/bank/auth/infrastructure/adapter/AuthControllerTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/bank/auth`
- `src/main/java/com/bank/auth/domain`
- `src/main/java/com/bank/auth/domain/model`
- `src/main/java/com/bank/auth/domain/port`
- `src/main/java/com/bank/auth/application`
- `src/main/java/com/bank/auth/application/service`
- `src/main/java/com/bank/auth/application/dto`
- `src/main/java/com/bank/auth/infrastructure`
- `src/main/java/com/bank/auth/infrastructure/adapter`
- `src/main/java/com/bank/auth/infrastructure/config`
- `src/main/java/com/bank/auth/infrastructure/security`
- `src/main/resources`
- `src/test/java/com/bank/auth`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con capas separadas (dominio, aplicación, infraestructura)**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollador, Tecnología Java, Advanced L2
- Brecha que el reto ataca: Experto en el marco de trabajo (framework) mas relevante en su lenguaje de programación principal, lo que le permite escribir código más legible y reducir la redundancia. Ha implementado al menos dos mecanismos de autenticación e identifica el uso de un JWT

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
