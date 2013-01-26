# Trello

Clojure wrapper for the Trello API.

To generate your api key and api token visit: https://trello.com/1/appKey/generate

## Usage

Trello is available from Clojars.org. To use it, add the following as a dependency in Leiningen.

```clojure
[trello "0.1.1-SNAPSHOT"]
```

Require the Trello.core namespace into your REPL or project

```clojure
(require 'trello.core)
```

# Authentication

This library does NOT implement OAuth. You'll need to get an access token from Trello. To do this you can use the following URL

https://trello.com/1/authorize?key=YOURKEY&name=My+Application&expiration=1day&response_type=token&scope=read,write

Since all requests need authentication you'll need to use the with-auth macro to wrap your requests.

```clojure

(with-auth api-key api-token
  (boards))

```

Alternatively just pass a settings map through to the auth! macro where :key is your Trello api key and :token is your auth token

```clojure

(:use [trello.client :as client])

(def settings {:key "MYKEY" :token "MYTOKEN"})

(client/auth! settings
  (boards))

```

## Members

The following API methods are available for querying member resources

## Boards

The following API methods are available for querying board resources

## Cards

The following API methods are available for querying card resources

## Lists

## Unit Tests

Unit tests are written in Midje, run them all once using:

```bash
lein midje
```

Or keep them running while developing with lazytest:

```bash
lein midje --lazytest
```

## Custom queries

If we have missed any API methods you might need you can perform an arbitray API call with the following method that takes two params the http method (e.g :get) and your query as a string.

```clojure
(api/api-request :http-method "yourquery")
```

TODO

Future releases will include the following:

Actions, Checklists, Notifications, Organizations

## License

Copyright © BoxUK

Distributed under the Eclipse Public License, the same as Clojure.
