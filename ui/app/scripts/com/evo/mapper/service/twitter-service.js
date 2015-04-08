angular.module('mapper-ui').service('TwitterService', ['$log', '$rootScope',
  function ($log, $rootScope) {
    var TwitterService = function (configIn) {
      var instance = {
        tweets: [],
        errors: [],
        pubsubSocket: undefined,
        pubsubStompClient: undefined,
        _getTweets: function () {
          return instance.tweets;
        },
        _onError: function (errorIn) {

        },
        _init: function (configIn) {
          instance._initPubsubSocket();
        },
        _initPubsubSocket: function(){
          $log.debug("Initialize pubsub socket");
          instance.pubsubSocket = new SockJS('/twitter');

          instance.pubsubStompClient = Stomp.over(instance.pubsubSocket);
          instance.pubsubSocket.onopen = function() {
            console.log('pubsubSocket open');
          };
          instance.pubsubSocket.onmessage = function(e) {
            console.log('pubsubSocket message', e.data);
            instance.tweets.push(e.data);
          };
          instance.pubsubSocket.onclose = function() {
            console.log('pubsubSocket close');
          };
          instance.pubsubStompClient.connect('user', 'password', function(frame){
              console.log('pubsubStompClient connect:', frame);
            instance.pubsubStompClient.subscribe('/stream',function(msg){
              console.log('pubsubStompClient streaming:', JSON.parse(msg.body));
              instance.tweets.push(JSON.parse(msg.body));
              $rootScope.$digest();

            });
          });

        },
        _destory: function () {
          instance.pubsubSocket.close();
          instance.errorSocket.close();
        }
      };
      instance._init(configIn);
      return {
        getTweets: instance._getTweets,
      };
    };
    return new TwitterService();
  }]
  );