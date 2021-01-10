'use strict';
angular.module('garden.services',['ngResource'])
    .factory('PersonService', ['$resource',function ($resource) {
        return $resource('persons/:personId',{personId:'@personId'});
    }])
    .factory('AddressService', ['$resource',function ($resource) {
        return $resource('persons/:personId/addresses/:addressId',
            {personId:'@personId',addressId:'@addressId'},
            {byPerson:{method:'GET',isArray:true}});
    }])
    .factory('AuthService',['$http','$rootScope',function(http,rootScope){

        return {
            authorize:function(params,successCall,errorCall) {
                http.post({
                    method:'POST',
                    url:'authorize',
                    data: $.params(params),
                    headers: {'Content-Type': 'application/json','Accept':'application/json'}
                })
                .success(function(data, status, headers, config) {
                    if(status == 200 && data.success == true) {
                        sessionStorage.setItem('user-roles',JSON.stringify(data));
                        successCall();
                    }
                    else {
                        errorCall()
                    }
                })
                .error(errorCall);
            },
            identity:function() {
                return JSON.parse(sessionStorage.getItem('user-roles'));
            },
            logout:function() {
                sessionStorage.removeItem('user-roles')
            }
        }
    }]);
