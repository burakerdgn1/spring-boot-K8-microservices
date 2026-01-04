# 🏦 Cloud-Native Banking Microservices Platform

A **cloud-native banking platform** built with **Spring Boot, Spring Cloud, Kubernetes, Kafka, and Helm**, demonstrating modern backend architecture patterns used in enterprise-grade systems.

> Focused on scalability, security, observability, and production-ready deployment practices.

---

## 🧩 Architecture Overview

The system is composed of multiple independently deployable microservices, secured and routed through an API Gateway and deployed on Kubernetes.

**Key architectural concepts:**
- API Gateway as a single entry point
- Kubernetes-native service discovery
- Event-driven communication with Kafka
- Centralized configuration management
- Full observability (metrics, logs, traces)

---

## 🛠 Tech Stack

**Backend & Cloud**
- Java 21
- Spring Boot 3
- Spring Cloud (Gateway, Config, Kubernetes)
- Spring Cloud Function

**Platform & Infrastructure**
- Kubernetes
- Helm (multi-environment setup)
- Docker (container-first builds)

**Security**
- Keycloak
- OAuth2 / OpenID Connect
- JWT-based authentication

**Messaging & Observability**
- Apache Kafka
- Prometheus, Grafana
- Loki, Tempo
- OpenTelemetry

---

## 🧱 Microservices

- **API Gateway**  
  Centralized routing, security, JWT validation, and resilience.

- **Config Server**  
  Centralized configuration for all services.

- **Accounts / Cards / Loans Services**  
  Core business services exposing REST APIs and publishing domain events.

- **Message Service**  
  Event-driven service consuming Kafka events to send notifications  
  (email / SMS) using Spring Cloud Function.

---

## 🔐 Security Model

- Authentication and authorization handled via **Keycloak**
- JWT tokens validated at the **API Gateway**
- Business services remain stateless and security-agnostic

---

## 📊 Observability

The platform is fully observable by default:
- **Metrics** via Prometheus
- **Logs** via Grafana Loki
- **Distributed tracing** via Grafana Tempo
- Actuator-based health checks and monitoring

---

## 📦 Deployment

- Fully deployable on Kubernetes using **Helm**
- Environment-specific setups:
  - `dev`
  - `qa`
  - `prod`
- Reusable common Helm chart for shared resources

---

## 🎯 Highlights

- Cloud-native microservices architecture
- Gateway-level security & resilience
- Event-driven design with Kafka
- Kubernetes-native service discovery
- Production-oriented observability stack
- Clean separation of concerns

---
