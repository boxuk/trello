# Trello

Clojure wrapper for the Trello API

## Usage

You need to set ENV variables for your Trello auth key and token. The two environment vars to set are

    export TRELLO_KEY="mykey"
    export TRELLO_TOKEN="mytoken"

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
