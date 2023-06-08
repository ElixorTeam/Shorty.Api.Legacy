# Link API

- format: json
- version: v1

### Note
- innerRef - name only!!!
- externalRef - full url address
- createDt - DateTime **UTC-0**

### GET
<details>
<summary>
    <code>GET</code>
    <code><b>shorty/api/links</b></code>
    <code>(all links)</code>
</summary>

##### Responses
> | http code | response |
> |-----------|----------|
> | `200`     | json     |


##### Response 200
```json
{
  "data": [
    {
      "title": "MyYoutube",
      "innerRef": "q2d23",
      "externalRef": "https://www.youtube.com/baggerfast",
      "uid": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
      "createDt": "2023-06-08T10:11:16.570+00:00"
    }
  ]
}
```
or
```json
{
  "data": []
}
```

</details>


<details>
<summary>
    <code>GET</code>
    <code><b>shorty/api/links/{uuid}</b></code>
    <code>(link by uuid)</code>
</summary>

##### Parameters
> | name   |  type     | data type | description   |
> |--------|-----------|-----------|---------------|
> | `uuid` |  required | UUID      | link identity |

##### Responses
> | http code | response                   | description                          |
> |-----------|----------------------------|--------------------------------------|
> | `200`     | json                       | success                              |
> | `400`     | {"error": "Bad Request"}   | <b>uuid</b> is not valid             |
> | `400`     | {"error": "LinkNotExists"} | link with <b>uuid</b> doesn't Exists |

##### Response 200
```json
{
  "title": "BaggerFast (Aleksandrov Daniil) · GitHub",
  "innerRef": "MyGit",
  "externalRef": "https://github.com/BaggerFast",
  "uid": "99a86603-c601-4ddd-95cd-bea1384dee74",
  "createDt": "2023-06-08T10:12:13.578+00:00"
}
```

</details>

<details>
<summary>
  <code>GET</code>
  <code><b>shorty/api/links/external_ref_by_inner/{innerRef}</b></code>
  <code>(redirect url by inner)</code>
</summary>

##### Parameters
> | name       |  type     | data type | description |
> |------------|-----------|-----------|-------------|
> | `innerRef` |  required | string    | inner url   |

##### Responses
> | http code | response                         | description                                          |
> |-----------|----------------------------------|------------------------------------------------------|
> | `200`     | json                             | success                                              |
> | `400`     | {"error": "Bad Request"}         | <b>innerRef</b> is not valid                         |
> | `400`     | {"error": "ExternaRefNotExists"} | <b>externalRef</b> by <b>innerRef</b> doesn't Exists |

##### Response 200
```json
{
  "externalRef": "https://github.com/BaggerFast"
}
```

</details>

### POST
<details>
<summary>
  <code>POST</code>
  <code><b>shorty/api/links/</b></code>
  <code>(create link)</code></summary>

##### Json Request Body
> | name          | type     | data type | description                                                                            |
> |---------------|----------|-----------|----------------------------------------------------------------------------------------|
> | `title`       | optional | string    | if not set, title sets from header of externalRef                                      |
> | `innerRef`    | optional | string    | - if not set, will be generated <br> - url name for our service (without full address) |
> | `externalRef` | required | string    | url (htpps, full domain) adress for redirect page                                      |


```json
{
  "title": "new link title"
}
```
##### Responses
> | http code | response                    | description                         |
> |-----------|-----------------------------|-------------------------------------|
> | `201`     | {"msg": "Success" }         | success                             |
> | `400`     | {"error": "Bad Request"}    | <b>innerRef</b>  is null or empty   |
> | `400`     | {"error": "AutoTitleError"} | <b>title</b> can't set automaticaly |
> | `400`     | {"error": "InnerRefExists"} | <b>innerRef</b> must be unique      |

</details>

### PUT
<details>
<summary>
  <code>PUT</code>
  <code><b>shorty/api/links/{uuid}</b></code>
  <code>(update link title by uuid)</code></summary>

##### Parameters
> | name   |  type     | data type | description   |
> |--------|-----------|-----------|---------------|
> | `uuid` |  required | UUID      | link identity |

##### Json Request Body
> | name    |  type    | data type | description       |
> |---------|----------|-----------|-------------------|
> | `title` | required | string    | new title of link |

```json
{
  "title": "new link title"
}
```
##### Responses
> | http code | response                   | description                          |
> |-----------|----------------------------|--------------------------------------|
> | `200`     | {"msg": "Success" }        | success                              |
> | `400`     | {"error": "Bad Request"}   | title is null or empty               |
> | `400`     | {"error": "LinkNotExists"} | link with <b>uuid</b> doesn't Exists |

</details>

### DELETE
<details>
<summary>
  <code>DELETE</code>
  <code><b>shorty/api/links/{uuid}</b></code>
  <code>(delete link by uuid)</code></summary>

##### Parameters
> | name   |  type     | data type | description   |
> |--------|-----------|-----------|---------------|
> | `uuid` |  required | UUID      | link identity |


##### Responses

> | http code | response                   | description                          |
> |-----------|----------------------------|--------------------------------------|
> | `200`     | {"msg": "Success" }        | success                              |
> | `400`     | {"error": "Bad Request"}   | <b>uuid</b> is not valid             |
> | `400`     | {"error": "LinkNotExists"} | link with <b>uuid</b> doesn't Exists |

</details>
