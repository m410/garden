'use strict';
angular.module('garden.controllers', ['garden.services'])
    .controller('PersonController', ['$scope', '$stateParams', 'PersonService',
        function (scope, stateParams, personService) {
            scope.persons = personService.query();
        }])
    .controller('PersonCreateController', ['$scope', '$location', 'PersonService',
        function (scope, location, personService) {
            scope.person= {};
            scope.update = function() {
                personService.save(scope.person);
                location.path('/persons');
            }
        }])
    .controller('PersonDetailController', ['$scope', '$stateParams', 'PersonService',
        function (scope, params, personService) {
            scope.person = personService.get({personId:params.personId});
        }])
    .controller('PersonEditController', ['$scope', '$location', '$stateParams', 'PersonService',
        function (scope, location, stateParams, personService) {
            scope.person = personService.get({personId:stateParams.personId});
            scope.update = function(person) {
                personService.save(person);
                location.path('/');
            }
        }])
    .controller('PersonDeleteController', ['$scope', '$stateParams', 'PersonService',
        function ($scope, stateParams, personService) {
            $scope.person = personService.get({personId:stateParams.personId });
        }])
    .controller('AddressListController', ['$scope', '$state', '$stateParams', 'AddressService',
        function ($scope, state, stateParams, addressService) {
            $scope.addresses = addressService.byPerson({personId: stateParams.personId });
            $scope.add = function() {
                state.go('persons-detail.address-create');
            };
            $scope.edit = function(id) {
                state.go('persons-detail.address-create',{addressId:id,personId:stateParams.personId});
            };
            $scope.delete = function(id) {
                addressService.delete({personId:stateParams.personId,addressId:id}).$promise.then(function () {
                    state.go('persons-detail.addresses-list');
                })
            };
        }])
    .controller('AddressCreateController', ['$scope', '$state', '$stateParams', 'AddressService',
        function ($scope, state, stateParams, addressService) {
            $scope.address = {street: 'b', street2: 'c', city: 'd', state: 'e', postalCode: '0' };
            $scope.save = function(addr) {
                addressService.save({personId:stateParams.personId},addr);
                state.go('persons-detail.addresses-list');
            }
        }])
;
