# Trello

Clojure wrapper for the Trello API

## Usage

You need to set ENV variables for your Trello auth key and token. The two environment vars to set are

    export TRELLO_KEY="mykey"
    export TRELLO_TOKEN="mytoken"


Require the Trello.core namespace into your REPL or project

    (require 'trello.core)

When hacking in the REPL you might want to quickly get a list of your board ids or users etc. There is a utility function
that lets you quickly inspect your results that can be used as follows.

    (def r (all-boards))

    (filter-by-param "id" r)

This will print out all the id's for your Trello boards.

The following methods all return a JSON response from the server.

Getting your member information

    (member)

Getting all your boards

    (all-boards)

Getting all the organizations you belong to

    (all-organizations)


## License

Copyright © BoxUK

Distributed under the Eclipse Public License, the same as Clojure.
