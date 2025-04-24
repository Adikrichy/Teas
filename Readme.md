## Application Initialization

If this is the first time you start up the application, the following data will be automatically inserted into the database:

### Preloaded Accounts

- **Super Admin Account** (with `ROLE_ADMIN` authority):

    - **Email:** `springadmin@gg.com`
    - **Password:** `password1`
    - **Nickname:** `superadmin`

- **3 User Accounts** (with `ROLE_USER` authority):

    - **Emails:**
        - `springuser@gg.com`
        - `springuser2@gg.com`
        - `springuser3@gg.com`
    - **Password:** `password1`
    - **Nickname:** `superuser`

### Categories

- Black Tea
- Oolong Tea
- Jasmine Tea
- Caffeine Free

### Milks

- NA
- No Milk
- Whole Milk
- Nonfat Milk
- Almond Milk
- Oat Milk
- Coconut Milk

### Sizes

- 8oz
- 12oz
- 16oz

### Menu Items

- Chai, Earl Grey, English Breakfast, Jasmine, Dragon Pearl, Silver Needle, Genmaicha
- Iced Strawberry Lemonade, Tumeric Ginger, Chamomile, Hibiscus Berry, Mint, Peppermint
- Frozen Lemonade, Hot Chocolate, Oolong, Iron Goddess of Mercy, High Mountain Tea

### Purchases

- 50 Purchases created by random user accounts with various menu items and custom options.

You're now ready to test out the API using any API client.

---

## API Endpoints

### Authentication Controller

#### `POST /auth/signup`

Registers a new user.

- **Request Body:**

```json
{
  "email": "john@doe.com",
  "password": "password",
  "nickname": "John Doe"
}
```

- Returns `201 CREATED` on success.
- Returns `409 CONFLICT` if email already exists.

#### `POST /auth/login`

Authenticates a user.

- **Request Body:**

```json
{
  "email": "john@doe.com",
  "password": "password1"
}
```

- Returns JWT token in a cookie and `200 OK` on success.
- Returns `401 UNAUTHORIZED` on invalid credentials.

#### `POST /auth/logout`

Logs the user out by clearing the security context and cookie.

- Returns `200 OK`

### Authorization Controller (Authenticated Users)

#### `GET /resource/user`

- Returns current authenticated user details (`200 OK`).

#### `PATCH /resource/user`

- Updates nickname.
- **Request Body:** `{ "nickname": "JohnUser" }`
- Returns updated user info (`200 OK`).

#### `PATCH /resource/updatePassword`

- **Request Body:**

```json
{
  "currentPassword": "password1",
  "newPassword": "password2"
}
```

- Verifies password and updates if valid. Returns `200 OK`.

### Public Resources

#### `GET /menuitems`

Returns all menu items.

#### `GET /bestseller`

Returns top 3 bestselling menu items based on quantity sold.

#### `GET /category/{id}/menuitems`

Returns menu items by category ID.

#### `GET /categories`

Returns all categories.

#### `GET /milks` and `GET /milks/{id}`

Returns all milks or one by ID.

#### `GET /sizes`

Returns all sizes.

#### `GET /sugars`

Returns all sugar options.

#### `GET /taxes/{state}`

Returns tax rate by U.S. state.

### Private Resources (ROLE\_ADMIN Only)

#### `POST /menuitem`

Creates a new menu item.

- **Example:**

```json
{
  "title": "Chai",
  "imageUrl": "",
  "description": "chai",
  "categoryId": 1,
  "milkId": 2,
  "temperature": "FREE",
  "sugar": "ZERO",
  "price": 5
}
```

#### `PATCH /menuitem/{id}`

Partially updates a menu item by ID.

#### `DELETE /menuitem/{id}`

Deletes menu item by ID.

#### `POST /menuitem/{id}/img`

Uploads image for a menu item.

#### `DELETE /menuitem/{id}/img`

Removes image from a menu item.

#### `PATCH /menuitem/{id}/toggleActive`

Toggles visibility of a menu item.

### Category, Milk, Size Controllers (Admin only)

- `POST`, `PATCH`, `DELETE` operations supported for:
    - `/category`
    - `/milk`
    - `/size`

### Admin Reports

- `GET /purchases/all`: all purchases
- `GET /accounts`: all user accounts
- `PATCH /accounts/{id}`: toggle account role
- `DELETE /accounts/{id}`: delete account

### Cart Controller (Authenticated Users)

#### `GET /carts`

Returns all carts of current user.

#### `POST /cart`

Creates a new cart.

- **Example:**

```json
{
  "menuitemId": 2,
  "milkId": 9,
  "sizeId": 2,
  "quantity": 1,
  "sugar": "TWENTY_FIVE",
  "temperature": "HOT"
}
```

#### `PUT /cart/{id}`

Updates a cart.

#### `DELETE /cart/{id}`

Deletes a cart by ID.

### Purchase Controller (Authenticated Users)

#### `GET /purchases`

Returns user's purchases.

#### `GET /purchases/{id}`

Returns a specific purchase.

#### `POST /purchase`

Creates a new purchase from cart.

- **Request Body:**

```json
{
  "tip": 5.5,
  "state": "WA"
}
```

#### `DELETE /purchase/{id}`

Deletes a purchase.

---

Thank you for reading! I hope this API helps you build an Teas platform using Spring Boot. Happy coding!

