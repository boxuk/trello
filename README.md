# Trello

Clojure wrapper for the Trello API

## Usage

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

The following methods all return a JSON response from the server.

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

# API METHOD DOCS

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

## Unit Tests

Unit tests are written in Midje, run them all once using:

```bash
lein midje
```

Or keep them running while developing with lazytest:

```bash
lein midje --lazytest
```

## License

Copyright © BoxUK

Distributed under the Eclipse Public License, the same as Clojure.
