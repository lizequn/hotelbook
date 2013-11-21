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

function MembersCtrl($scope, $http, Members) {

    $scope.refresh = function () {
        $scope.members = Members.query();

    };


    $scope.reset = function () {
        // clear input fields
        $scope.newMember = {};
    };

    $scope.register = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};
        Members.save($scope.newMember, function (data) {
            $scope.successMessages = [ 'Member Registered' ];
            $scope.refresh();
            $scope.reset();


        }, function (result) {

            if ((result.status == 409) || (result.status == 400)) {
                Data.email = '';
                $scope.errors = result.data;
                $scope.errorMessages = [ 'Error: Customer registration error in server Book Taxi ' ];
            } else {
                Data.email = '';
                $scope.errorMessages = [ 'Unknown  server error' ];
            }
        });

    };
    $scope.refresh();
    $scope.reset();
    $scope.orderBy = 'name';
}

function FlightCtrl($scope, $http, Hotels){
    $scope.refresh = function (){
        $scope.flights = Hotels.query();
    };
    $scope.reset = function () {
        // clear input fields
        $scope.newFlight = {};
    };
    $scope.register = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};

        Hotels.save($scope.newFlight, function (data) {
//            console.log(header("info"));
            // mark success on the registration form
            $scope.successMessages = [ 'Member Registered' ];

            // Update the list of members
            $scope.refresh();

            // Clear the form
            $scope.reset();
        }, function (result) {
            if ((result.status == 409) || (result.status == 400)) {
                $scope.errors = result.data;
            } else {
                $scope.errorMessages = [ 'Unknown  server error' ];
            }
            $scope.$apply();
        });
    };

    $scope.refresh();
    $scope.reset();
    $scope.orderBy = 'flight_no';


}

function getObject(re){
    var result = new Object();
    for(var p in re){
        console.log(re[p]);
        if(typeof re[p] != 'function'){
           result.p = re[p];

        }
    }
    return result;
}

function BookCtrl($scope,Books,Members,Hotels){
    $scope.refresh = function (){
        $scope.books = Books.query();
        $scope.flights = Hotels.query();
        $scope.members = Members.query();
    };
    $scope.refresh();
    $scope.orderBy = 'id';
}