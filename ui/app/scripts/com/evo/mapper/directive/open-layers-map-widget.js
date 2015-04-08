angular.module('mapper-ui').directive('openLayersMapWidget', ['$rootScope',
  function($rootScope) {
    return {
      restrict: 'E',
      replace: false,
      controller: 'OpenLayersMapWidgetController',
      link: function($scope, element, attrs) {
        $scope.render(element);
      },
      templateUrl: 'templates/com/evo/mapper/open-layers-map-widget.html'
    };
  }
]);