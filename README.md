# discljord-template

A Leiningen template for [discljord](https://github.com/IGJoshua/discljord) projects.

## Usage

Run `lein new discljord <project-name>`.

The project that will be created contains code for a simple bot that responds with 
a random greeting from a configurable list when it's pinged.

- To play around with the API in the repl, run `lein repl` and use `start-bot!` to get a map containing 
an event, connection and messaging channel.
- To run it as an application with the greeting bot functionality, 
set a token in the `config.edn` file and run `lein run`.

Example REPL session:
```clojure
first-bot.core=> (use 'clojure.pprint 'clojure.core.async)
nil
first-bot.core=> (def bot (start-bot! "TOKEN" :guild-messages)) ; guild-messages is a gateway intent, enables message event reception
#'first-bot.core/bot
first-bot.core=> (pprint (<!! (:events bot))) ; waiting for the next event 
[:message-create
 {:mention-everyone false,
  :mentions []
  :pinned false,
  :content "Can you hear me?",
  :attachments [],
  :mention-roles [],
  :type 0,
  :guild-id "445994327152263179",
  :author
  {:username "Johnny",
   :public-flags 640,
   :id "234343108773412864",
   :discriminator "3826",
   :avatar "a_cdc528b98208e58a43fcbc471a0b0ccc"},
  :member
  {:roles ["607624294784040960"],
   :premium-since nil,
   :nick nil,
   :mute false,
   :joined-at "2018-05-15T17:02:13.810000+00:00",
   :hoisted-role nil,
   :deaf false},
  :id "736626702788067378",
  :channel-id "588374933277769728",
  :embeds [],
  :timestamp "2020-07-25T16:51:31.807000+00:00",
  :flags 0,
  :nonce "736626702230224896",
  :tts false,
  :edited-timestamp nil}]
nil
first-bot.core=> (discord-rest/create-message! (:rest bot) "588374933277769728" :content "Hi from Clojure!")
#object[discljord.messaging.impl.DerefablePromiseChannel 2d74724a "discljord.messaging.impl.DerefablePromiseChannel@2d74724a"]
```

## License

Copyright © 2020, 2023 JohnnyJayJay

This software and accompanying materials are made available under the terms of the MIT License, which is available at
https://mit-license.org

For your convenience, the files in `resources/` (which are copied to your project when applying this template) are additionally made available under the terms of the BSD Zero Clause License, which is available at
https://opensource.org/license/0bsd/
