# Magtu pairs api.
### What does it do?
- Provides easy access to parsed pairs.
- Makes possible to create your own bot implementations.

## How to use?
### Do a request.
```sh
GET: https://magtu.bot/tables/latest
```
### Get a response.
```js
"items": [
    {
        "group": [
            String, String,
        ],
        "date": Date,
        "pairs": [
            {
                "changes": Boolean,
                "removed": Boolean,
                "error": Boolean,
                "number": Number,
                "subgroup": String,
                "name": String,
                "teacher": String,
                "classroom": String
            },
            // ...
        ],
        "displayName": String
    }
// ...
]
```
# Documentation
If you want to more know please continue in:
```sh
  https://ivanik.ru/mpk/docs/api/
```
You can also reache me on Discord `Elerphore#7557` or on [Telegram](https://t.me/elerphore)
