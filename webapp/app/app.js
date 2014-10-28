'use strict';
angular.module('garden.app', ['ui.router','garden.services','garden.controllers'])
    .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider,   $urlRouterProvider) {
        $urlRouterProvider.otherwise('/');
        $stateProvider
            .state('home',{
                url:'/',
                controller:'PersonController',
                templateUrl:'fragments/home.html'
            })
            .state('persons',{
                url:'/persons',
                controller:'PersonController',
                templateUrl:'fragments/person/list.html'
            })
            .state('persons-create',{
                url:'/persons/create',
                controller:'PersonCreateController',
                templateUrl:'fragments/person/form.html'
            })
            .state('persons-detail',{
                url:'/persons/:personId',
                views: {
                    '':{
                        controller:'PersonDetailController',
                        templateUrl:'fragments/person/detail.html'
                    },
                    'addresses@persons-detail': {
                        controller: 'AddressListController',
                        templateUrl: 'fragments/address/list.html'
                    }
                }
            })
            .state('persons-detail.address-create',{
                views: {
                    'addresses@persons-detail': {
                        controller: 'AddressCreateController',
                        templateUrl: 'fragments/address/form.html'
                    }
                }
            })
            .state('persons-detail.addresses-list',{
                views: {
                    'addresses@persons-detail': {
                        controller: 'AddressListController',
                        templateUrl: 'fragments/address/list.html'
                    }
                }
            })
            .state('persons-edit',{
                url:'/:personId/edit',
                controller:'PersonEditController',
                templateUrl:'fragments/person/form.html'
            });
    }])
    .run(['$rootScope', '$state', '$stateParams', function ($rootScope, $state, $stateParams) {
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
    }]);
