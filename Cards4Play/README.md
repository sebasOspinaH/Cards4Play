# Cards4Play — Backend API

Sistema backend para la empresa **Cards4Play**, especializada en comercialización de productos TCG y organización de torneos.

---

## Tecnologías

| Tecnología   | Uso                          |
|-------------|------------------------------|
| Java 17      | Lenguaje principal            |
| Spark Java   | Microframework REST           |
| Gson         | Serialización JSON            |
| Maven        | Gestión de dependencias       |
| Docker       | Contenerización               |
| exchangerate-api | Tasa USD → COP en tiempo real |

---

## Correr localmente

### Con Docker (recomendado)

```bash
# Construir y levantar
docker-compose up --build

# Solo construir la imagen
docker build -t cards4play .

# Correr el contenedor
docker run -p 8080:8080 -v cards4play-data:/app/data cards4play
```

### Con Maven

```bash
mvn package
java -jar target/cards4play-jar-with-dependencies.jar
```

La API estará disponible en: `http://localhost:8080`

---

## Autenticación

Todas las rutas protegidas requieren estas cabeceras HTTP:

```
X-User-Email: admin@cards4play.com
X-User-Password: admin123
```

**Admin por defecto:**
- Email: `admin@cards4play.com`
- Password: `admin123`

---

## Endpoints

### Health Check
```
GET /health
```

### Autenticación
```
POST /auth/login
Body: { "email": "...", "password": "..." }
```

---

### Clientes (requiere ADMIN salvo donde se indica)

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/clients` | Listar todos los clientes |
| POST | `/clients` | Registrar nuevo cliente |
| GET | `/clients/:id` | Ver cliente (ADMIN o propio cliente) |
| PUT | `/clients/:id` | Actualizar cliente |
| DELETE | `/clients/:id` | Eliminar cliente |
| GET | `/clients/:id/inventory` | Ver inventario del cliente |
| GET | `/clients/:id/boosters` | Ver boosters del cliente |
| POST | `/clients/:id/boosters/:boosterId/open` | Abrir un booster |
| GET | `/clients/:id/purchases` | Historial de compras |

**Registrar cliente:**
```json
POST /clients
{
  "identification": "CLI-001",
  "name": "Juan Pérez",
  "email": "juan@ejemplo.com",
  "password": "pass123"
}
```

---

### Inventario de la tienda

#### Cartas
```
GET    /inventory/cards          → Listar cartas (autenticado)
GET    /inventory/cards/:id      → Ver carta
POST   /inventory/cards          → Crear carta (ADMIN)
PUT    /inventory/cards/:id      → Actualizar carta (ADMIN)
DELETE /inventory/cards/:id      → Eliminar carta (ADMIN)
```

**Crear carta** (el precio COP se calcula automáticamente con la tasa vigente):
```json
POST /inventory/cards
{
  "identification": "CARD-001",
  "name": "Black Lotus",
  "priceUSD": 25.50,
  "rarity": "MYTHIC"
}
```
> Raridades válidas: `COMMON`, `UNCOMMON`, `RARE`, `MYTHIC`

#### Boosters
```
GET    /inventory/boosters       → Listar boosters
GET    /inventory/boosters/:id   → Ver booster
POST   /inventory/boosters       → Crear booster (ADMIN) — genera 10 cartas aleatorias
DELETE /inventory/boosters/:id   → Eliminar booster (ADMIN)
```

**Crear booster:**
```json
POST /inventory/boosters
{
  "identification": "BST-001",
  "name": "Booster Pack Alpha",
  "priceUSD": 5.00
}
```

#### Productos Sellados
```
GET    /inventory/sealed         → Listar
POST   /inventory/sealed         → Crear (ADMIN)
DELETE /inventory/sealed/:id     → Eliminar (ADMIN)
```
```json
POST /inventory/sealed
{
  "identification": "SEA-001",
  "name": "Starter Deck",
  "priceUSD": 15.00,
  "edition": "Base Set"
}
```

#### Accesorios
```
GET    /inventory/accessories        → Listar
POST   /inventory/accessories        → Crear (ADMIN)
DELETE /inventory/accessories/:id    → Eliminar (ADMIN)
```
```json
POST /inventory/accessories
{
  "identification": "ACC-001",
  "name": "Dragon Shield Matte",
  "priceUSD": 8.00,
  "accessoryType": "SLEEVE"
}
```

---

### Torneos

```
GET    /tournaments                          → Listar torneos
GET    /tournaments/:id                      → Ver torneo
POST   /tournaments                          → Crear torneo (ADMIN)
PUT    /tournaments/:id                      → Actualizar (ADMIN)
DELETE /tournaments/:id                      → Eliminar (ADMIN)
GET    /tournaments/:id/participants         → Ver participantes
POST   /tournaments/:id/register             → Inscribirse (cliente) o inscribir cliente (admin)
DELETE /tournaments/:id/register/:clientId   → Desinscribir (ADMIN)
```

**Crear torneo:**
```json
POST /tournaments
{
  "id": "TORN-001",
  "name": "Copa Cards4Play 2025",
  "date": "2025-12-15",
  "capacity": 32
}
```

**Inscripción (como cliente):**
```
POST /tournaments/TORN-001/register
(sin body, usa el cliente autenticado)
```

**Inscripción (como admin):**
```json
POST /tournaments/TORN-001/register
{
  "clientId": "CLI-001"
}
```

---

### Compras

```
POST /purchases         → Realizar compra
GET  /purchases         → Listar todas las compras (ADMIN)
GET  /purchases/:id     → Ver detalle de compra
```

**Realizar compra:**
```json
POST /purchases
{
  "productIds": ["CARD-001", "BST-001", "ACC-001"]
}
```
> Si el admin hace la compra, agregar: `"clientId": "CLI-001"`

---

## Persistencia

Los datos se guardan automáticamente en `data/state.json` tras cada operación. Al reiniciar la aplicación, el estado se recupera del archivo.

Con Docker, usar un volumen para que los datos sobrevivan entre reinicios:
```bash
docker run -p 8080:8080 -v $(pwd)/data:/app/data cards4play
```

---

## Variables de entorno

| Variable | Default | Descripción |
|----------|---------|-------------|
| `PORT` | `8080` | Puerto del servidor |
| `DATA_PATH` | `data/state.json` | Ruta del archivo de persistencia |
