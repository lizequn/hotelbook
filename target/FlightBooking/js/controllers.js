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

function MembersCtrl($scope,$location, $http, Members,Members4Taxi,Data) {

    $scope.refresh = function () {
        $scope.members = Members.query();

    };


    $scope.reset = function () {
        // clear input fields
        $scope.newMember = {};
        $scope.newMember4Taxi = {};
    };

    $scope.register = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};
        Data.email = $scope.newMember.email;
        console.log($scope.newMember);
        Members.save($scope.newMember, function (data) {

            Members4Taxi.save($scope.newMember,function(data){
                $scope.successMessages = [ 'Member Registered' ];
                $scope.refresh();
                $scope.reset();
                $location.path('/books');
            },function(result){


                if ((result.status == 409) || (result.status == 400)) {
                    if(result.status == 409){

                    }else{
                        Data.email = '';
                        $scope.errors = result.data;
                        $scope.errorMessages = [ 'Error: Customer registration error in server Book Taxi ' ];
                    }
                } else {
                    Data.email = '';
                    $scope.errorMessages = [ 'Unknown  server error' ];
                }
                $scope.$apply();
            });

        }, function (result) {

            if ((result.status == 409) || (result.status == 400)) {
                console.log(result.data);
                if(result.status == 409){
                      console.log("right");
                    Members4Taxi.save($scope.newMember,function(data){
                        $scope.successMessages = [ 'Member Registered' ];
                        $scope.refresh();
                        $scope.reset();
                        $location.path('/books');
                    },function(result){


                        if ((result.status == 409) || (result.status == 400)) {
                            if(result.status == 409){

                            }else{
                                Data.email = '';
                                $scope.errors = result.data;
                                $scope.errorMessages = [ 'Error: Customer registration error in server Book Taxi ' ];
                            }
                        } else {
                            Data.email = '';
                            $scope.errorMessages = [ 'Unknown  server error' ];
                        }
                        $scope.$apply();
                    });

                }else{
                    Data.email = '';
                    $scope.errors = result.data;
                    $scope.errorMessages = [ 'Error: Customer registration error in server Book Flight ' ];
                }
            } else {
                Data.email = '';
                console.log(result);
                $scope.errorMessages = [ 'Unknown  server error' ];
            }
            $scope.$apply();
        });

    };
    $scope.refresh();
    $scope.reset();
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

function BookCtrl($scope,$location,$http,Books,Members,Flights,Members4Taxi,Taxis,Books4Taxis,Data){
    $scope.refresh = function (){
        $scope.books = Books.query();
        $scope.flights = Flights.query();
        $scope.taxis = Taxis.query();
        $scope.members = Members.query();
        $scope.member4Taxi =Members4Taxi.query();
    };
    $scope.reset = function () {
        // clear input fields
        $scope.newBook4Flight = {};
        $scope.newBook4Taxi = {};


    };
    $scope.initVar = function(){
        $scope.reset();
        $scope.preBook = {flight_id : -1 , taxi_id : -1,member4taxi_id:-1,member4flight_id:-1};
        $scope.del = {id:-1};
        //
         console.log(Data);
        if(Data.email == ''){
            $location.path('/members');
        }
        Members.get({memId:Data.email,email:Data.email},function(data){
            $scope.UserInfo = data;
        });

//        for(var p in $scope.members){
//            console.log(p);
//            if(p.email == Data.email){
//
//                console.log(p);
//                $scope.member4bookflight = p;
//                $scope.newBook4Flight.member = p;
//            }
//        }
//        for(var p in $scope.member4Taxi){
//            if(p.driverLicenseID == Data.driverLicenseID){
//                $scope.member4booktaxi = p;
//                $scope.newBook4Taxi.customer = p;
//            }
//        }

    };

    $scope.register = function(){
        Members.get({memId:Data.email,email:Data.email},function(data){
            $scope.newBook4Flight.member = data;
            Members4Taxi.get({memId:Data.email,email:Data.email},function(data){
                $scope.newBook4Taxi.customer = data;
                $scope.prepare4register();
            });
        });
    };

    $scope.prepare4register = function(){
        Flights.get({flightId:$scope.preBook.flight_id},function(fdata){
            $scope.newBook4Flight.flight = fdata;
            Taxis.get({taxiId:$scope.preBook.taxi_id},function(tdata){
                $scope.newBook4Taxi.taxi = tdata;
                $scope.newBook4Taxi.contractDate = "2012-12-10";
                $scope.ready4register();
            });
        });
    };
    $scope.ready4register = function(){
//        console.log($scope.newBook4Taxi);
//        console.log($scope.newBook4Taxi.customer);
//        console.log($scope.newBook4Taxi.taxi);
//        console.log($scope.newBook4Flight);
        Books.save($scope.newBook4Flight,function(data){
            console.log("save flight success");
            $scope.successMessages = '';
            $scope.errorMessages = '';
            $scope.errors = {};
           Books4Taxis.save($scope.newBook4Taxi,function(data){
            console.log("save taxi success");
            $scope.successMessages = [ 'Member Registered' ];
            $scope.refresh();
            $scope.initVar();
           },function(result){
               cnosole.log("save taxi fail");
              //error roll back; for taxi book
               Books.delete({bookId:$scope.newBook4Flight.flight.id,delId:$scope.newBook4Flight.member.id},function(data){
                   console.log(data);
                   $scope.refresh();
                   console.log("flight rollback");
               });

           });
        },function(result){
            console.log("book error");
            //error for flight boook
            if ((result.status == 409) || (result.status == 400)) {
                $scope.errorMessages = result.data;
            } else {
                $scope.errorMessages = [ 'Unknown  server error' ];
            }
        });
    };

//
//    $scope.registerdelete = function(){
//        $scope.reset();
//        if($scope.preBook.flight_id != -1 && $scope.preBook.member_id != -1) {
//            Flights.get({flightId: $scope.preBook.flight_id},function(fdata){
//                $scope.newBook.flight = fdata;
//                console.log($scope.newBook.flight);
//                Members.get({memId:$scope.preBook.member_id},function(mdata){
//                    $scope.newBook.member = mdata;
//                    $scope.readyregister();
//                    $scope.preBook.flight_id = -1 ;
//                    $scope.preBook.member_id = -1 ;
//
//                });
//            });
//        }
//
//
//    };
//    $scope.readyregister = function () {
//        $scope.successMessages = '';
//        $scope.errorMessages = '';
//        $scope.errors = {};
////        $scope.newBook = {flight:{company: "fly"  ,
////            current_num: 22 ,
////            date: "2009-01-01" ,
////            flight_no: "aa33",
////            flight_size: 33,
////            flight_type: "b333",
////            id: 0 ,
////            time: "23:21"},member:{
////            email: "john.smi@Aaa.com",
////            id: 3,
////            name: "Hello Wolrd",
////            phoneNumber: "21255251212"
////        }};
//        Books.save($scope.newBook, function (data) {
//
//            // mark success on the registration form
//            $scope.successMessages = [ 'Member Registered' ];
//
//            // Update the list of members
//            $scope.refresh();
//
//            // Clear the form
//            $scope.reset();
//        }, function (result) {
//            if ((result.status == 409) || (result.status == 400)) {
//                $scope.errorMessages = result.data;
//            } else {
//                $scope.errorMessages = [ 'Unknown  server error' ];
//            }
//
//        });
//
//
//    };

//    $scope.delBook = function (){
//        $scope.delError='';
//       // console.log($scope.del.id);
//        Books.delete({bookId:$scope.del.id},function(data){
//            //console.log(data);
//            $scope.refresh();
//        });
//
//    };


    $scope.refresh();
    $scope.initVar();
    $scope.orderBy = 'id';


}