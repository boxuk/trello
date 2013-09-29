# Trello

Clojure wrapper for the Trello API.

To generate your api key and api token visit: https://trello.com/1/appKey/generate

## Usage

Trello is available from Clojars.org. To use it, add the following as a dependency in Leiningen.

```clojure
[trello "0.1.1-SNAPSHOT"]
```

# Authentication

This library does NOT implement OAuth. You'll need to get an access token from Trello. To do this you can use the following URL

https://trello.com/1/authorize?key=YOURKEY&name=My+Application&expiration=1day&response_type=token&scope=read,write

## Examples

```
(ns trello.examples
  (:require [trello.core]))

(def auth 
  {:key "YOURKEY"
   :token "YOURAUTHTOKEN"})

(def all-boards (board-all auth))
```

Fetch a list of active Trello boards

```clojure
(def auth {:key "mykey" :secret "mysecret"})

(board-names auth)

;; => ("Barnaby Edwards" "Business" "Gather Requirements (product backlog)" "General" "Programming/Study")
```

## License

Copyright © BoxUK

Distributed under the Eclipse Public License, the same as Clojure.
