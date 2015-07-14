## This repository is no longer maintained

**We recommend [this fork](https://github.com/bsima/trello) instead.**

# Trello

Clojure wrapper for the Trello API.

To generate your api key and api token visit: https://trello.com/1/appKey/generate

## Usage

Trello is available from Clojars.org. To use it, add the following as a dependency in Leiningen.

```clojure
[trello "0.1.2-SNAPSHOT"]
```

# Authentication

This library does NOT implement OAuth.

You'll need to get an access token from Trello. To do this you can use the following URL

https://trello.com/1/authorize?key=YOURKEY&name=My+Application&expiration=never&response_type=token&scope=read,write

## Examples

The first things you'll need to do are to fetch an API key and auth token

```clojure
(require [trello.core :as trello])

(def auth 
  {:key "YOURKEY"
   :token "YOURAUTHTOKEN"})
```

Listing all boards

```clojure

(def auth 
  {:key "YOURKEY"
   :token "YOURAUTHTOKEN"})
   
(def all-boards (trello/boards auth))

(doseq [board all-boards]
  (println (:name board)))
  
```

Fetch a list of active Trello boards

```clojure

(trello/active-board-names auth)

;; => ("Barnaby Edwards" "Business" "Gather Requirements (product backlog)" "General" "Programming/Study")
```

## CLI

You can run this as a command line app using Lein. The CLI requires a file called config.clj to be
set in the root directory with the following info

config.clj

```clojure
{
  :key "YOURKEY"
  :token "YOURTOKEN"
}
```

Show all your Trello boards

```
lein run boards
```

## General notes

+ A memoized request can be produced by using the m- version of a function. I.e m-boards

## License

Copyright © BoxUK

Distributed under the Eclipse Public License, the same as Clojure.
