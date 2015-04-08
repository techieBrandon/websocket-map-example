angular.module('mapper-ui').controller('OpenLayersMapWidgetController', [
    '$scope', '$log', 'TwitterService',
    function ($scope, $log, TwitterService) {


      // $scope.tweets = TwitterService.getTweets();

      
     
      var Controller = function (configIn) {
        var instance = {
          widget: undefined,
          bubbles: [],
          _init: function (configIn) {
          },
          _updateData: function(dataIn){
            console.log({ dataIn: dataIn });
            if(angular.isDefined(dataIn) && angular.isArray(dataIn)){
              dataIn.forEach(function(datum){
                var bubble = angular.extend({
                  radius: 5,
                  name: datum.user.screenName
                }, datum.geoLocation);
                instance.bubbles.push(bubble);
              });
            }
          },
          _reDraw: function(){
            if(angular.isDefined(instance.widget)){
              instance.widget.bubbles(instance.bubbles);
            }
          },
          _render: function(element) {
            console.log({element:element})
            instance.widget = new Datamap({element: element.find('#map')[0] });
            instance.widget.bubbles(instance.bubbles,{

            });
          }
        };

        instance._init(configIn);

        return {
          render: instance._render,
          reDraw: instance._reDraw,
          updateData: instance._updateData
        };
      };

      var controller = new Controller();

      console.log({ TwitterService:TwitterService });
      $scope.$watch(function(){
        console.log({ tweets: TwitterService.getTweets().length });
        return TwitterService.getTweets().length;
      },function(){
          console.log({ arguments: arguments });
          controller.updateData(TwitterService.getTweets());
          controller.reDraw();
      },true);

      angular.extend($scope, controller);

    }
  ]);