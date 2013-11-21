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

    // Define a refresh function, that updates the data from the REST service
    $scope.refresh = function () {
        $scope.members = Members.query();

    };

    // Define a reset function, that clears the prototype newMember object, and
    // consequently, the form
    $scope.reset = function () {
        // clear input fields
        $scope.newMember = {};
    };

    // Define a register function, which adds the member using the REST service,
    // and displays any error messages
    $scope.register = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};

        Members.save($scope.newMember, function (data) {

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

    // Call the refresh() function, to populate the list of members
    $scope.refresh();

    // Initialize newMember here to prevent Angular from sending a request
    // without a proper Content-Type.
    $scope.reset();

    // Set the default orderBy to the name property
    $scope.orderBy = 'name';
}

function FlightCtrl($scope, $http, Flights){
    $scope.refresh = function (){
        $scope.flights = Flights.query();
    };
    $scope.reset = function () {
        // clear input fields
        $scope.newFlight = {};
    };
    $scope.register = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};

        Flights.save($scope.newFlight, function (data) {

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

function BookCtrl($scope, $http,Books,Members,Flights){
    $scope.refresh = function (){
        $scope.books = Books.query();
        $scope.members = Members.query();
        $scope.flights = Flights.query();
    };
    $scope.reset = function () {
        // clear input fields
        $scope.newBook = {};


    };
    $scope.initVar = function(){
        $scope.reset();
        $scope.preBook = {flight_id : -1 , member_id : -1}   ;
        $scope.del = {id:-1};

    } ;

    $scope.register = function(){
        $scope.reset();
        if($scope.preBook.flight_id != -1 && $scope.preBook.member_id != -1) {
            Flights.get({flightId: $scope.preBook.flight_id},function(fdata){
                $scope.newBook.flight = fdata;
                console.log($scope.newBook.flight);
                Members.get({memId:$scope.preBook.member_id},function(mdata){
                    $scope.newBook.member = mdata;
                    $scope.readyregister();
                    $scope.preBook.flight_id = -1 ;
                    $scope.preBook.member_id = -1 ;

                });
            });
        }


    } ;
    $scope.readyregister = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};
//        $scope.newBook = {flight:{company: "fly"  ,
//            current_num: 22 ,
//            date: "2009-01-01" ,
//            flight_no: "aa33",
//            flight_size: 33,
//            flight_type: "b333",
//            id: 0 ,
//            time: "23:21"},member:{
//            email: "john.smi@Aaa.com",
//            id: 3,
//            name: "Hello Wolrd",
//            phoneNumber: "21255251212"
//        }};
        Books.save($scope.newBook, function (data) {

            // mark success on the registration form
            $scope.successMessages = [ 'Member Registered' ];

            // Update the list of members
            $scope.refresh();

            // Clear the form
            $scope.reset();
        }, function (result) {
            if ((result.status == 409) || (result.status == 400)) {
                $scope.errorMessages = result.data;
            } else {
                $scope.errorMessages = [ 'Unknown  server error' ];
            }

        });


    };

    $scope.delBook = function (){
        $scope.delError='';
       // console.log($scope.del.id);
        Books.delete({bookId:$scope.del.id},function(data){
            //console.log(data);
            $scope.refresh();
        });

    };


    $scope.refresh();
    $scope.initVar();
    $scope.orderBy = 'id';


}