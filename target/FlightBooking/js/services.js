/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Define the REST resource service, allowing us to interact with it as a high level service
angular.module('membersService', ['ngResource']).
    factory('Members', function ($resource) {
        return $resource('rest/members/:memId/:email', {});
    });
angular.module('flightsService', ['ngResource']).
    factory('Flights', function ($resource) {
        return $resource('rest/flights/:flightId', {});
    });
angular.module('bookService', ['ngResource']).
    factory('Books', function ($resource) {
        return $resource('rest/books/:bookId/:delId', {});
    });
angular.module('memberService4Taxi', ['ngResource']).
    factory('Members4Taxi', function ($resource) {
        return $resource('http://kitchensinkangularjs-130534516.rhcloud.com/rest/customers/:memId/:email', {});
    });
angular.module('taxisService', ['ngResource']).
    factory('Taxis', function ($resource) {
        return $resource('http://kitchensinkangularjs-130534516.rhcloud.com/rest/taxis/:taxiId', {});
    });
angular.module('bookService4Taxi', ['ngResource']).
    factory('Books4Taxis', function ($resource) {
        return $resource('http://kitchensinkangularjs-130534516.rhcloud.com/rest/contracts/:bookId', {});
    });

angular.module('dataService',[]).factory('Data',function(){
    return {
       email:''
    }
});