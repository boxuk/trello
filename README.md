# Trello

Clojure wrapper for the Trello API. If you would like to contribute feel free to fork the project, start hacking, and submit a pull request.

## Usage

Trello is available from Clojars.org. To use it, add the following as a dependency in Leiningen.

```clojure
[trello "0.1.1-SNAPSHOT"]
```

You need to set ENV variables for your Trello auth key and token. The two environment vars to set are

```bash
export TRELLO_KEY="mykey"
export TRELLO_TOKEN="mytoken"
```

Require the Trello.core namespace into your REPL or project

```clojure
(require 'trello.core)
```

When hacking in the REPL you might want to quickly get a list of your board ids or users etc. There is a utility function
that lets you quickly inspect your results that can be used as follows.

```clojure
(def r (all-boards))

(filter-by-param "id" r)
```

This will print out all the id's for your Trello boards.

The following methods all convert JSON results into clojure maps.

## Get all queries

Getting your member information

```clojure
(member)
```

Getting all your boards

```clojure
(get-all :boards)
```

Getting all the organizations you belong to

```clojure
(get-all :oranizations)
```

Getting all cards

```clojure
(get-all :cards)
```

## Board API

Get all boards

```clojure
(all-boards)
```

Get a single board 

```clojure
(get-board id)
```

Get all the lists for a board

```clojure
(all-lists-for-board id)
```

## Member API

Get your member information

```clojure
(member)
```

Get a member by id

```clojure
(member id)
```

Member actions

```clojure
(member id "actions")
```

Member boards

```clojure
(member id "boards")
```

Member cards

```clojure
(member id "cards")
```

Member notifications

```clojure
(member id "notifications")
```

Member organizations

```clojure
(member id "organizations")
```

## Lists API

Get a single list

```clojure
(get-list id)
```

Get all the cards for a list

```clojure
(get-list-cards list_id)
```

## Cards

Get a card by id

```clojure
(get-card id)
```

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
