# **Around Me**

## Table of Contents

1. [App Overview](#App-Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Build Notes](#Build-Notes)

## App Overview

### Description 

**AroundMe is a mobile application designed to facilitate event discovery and engagement for users based on their current location. By utilizing location-based technology and intuitive user interfaces, AroundMe aims to offer a seamless and personalized experience for event exploration and participation.**

### App Evaluation

<!-- Evaluation of your app across the following attributes -->

- **Category:* Lifestyle*
- **Mobile:* Mobile is essential for the user to instantly see all the events happening in their proximity and the location features built into a phone*
- **Story:* User is bored without anything to do, can open the app and find a fun event nearby that interests them and knows when and where to attend the event.*
- **Market:* Anyone looking for community and events in their area.  Especially: people who are new to a certain area, college students, travellers etc.*
- **Habit:* We anticipate that people would make this app a one stop shop to events happening around them and plan their days out*
- **Scope:* V1 would simply display the events happening  near the user's location on the map. V2 would curate lists of events they like or want to attend and let users change date and location of search, V3 would let the users rate the events happening, and see the ratings of other users.*

## Product Spec

### 1. User Features

Required Features:

- Map-based interface
- Daily events represented as points on the map
- Allow users to click on the event and see a pop-up with more information on it
- Change date 
- Change location


Stretch Features:

- Allowing the users to scroll out (to a certain extent)
- Add to Wishlist
- Rate events and organizers
- Curate events according to users preferences

### 2. Chosen API(s)

- **[SerpApi - Google events API](https://serpapi.com/google-events-api)**
  - This api will provide the events happening and their related details given a location
- **[Google maps API](https://developers.google.com/maps/documentation/android-sdk)**
    - This API will provide the maps interface for the application

### 3. User Interaction

### Event Interaction
- **Viewing Event Details**
  - **Action:** Users tap on any event pin visible on the map.
  - **Response:** A pop-up window appears displaying detailed information about the event, including the title, description, date, and time, along with a close button ('X') to exit the window.

### Location Search
- **Changing Location**
  - **Action:** Users enter a new location in the location textbox.
  - **Response:** The map refreshes to center on the entered location, simultaneously updating to show events specific to this area, allowing users to explore events in different regions or plan visits in advance.

### Date Navigation
- **Exploring Events by Date**
  - **Action:** Users select a new date using the date input tool.
  - **Response:** The map updates to display events occurring on the chosen date, enabling users to plan for upcoming events or reflect on past ones.

## Wireframes

<!-- Add picture of your hand sketched wireframes in this section -->
<img src="https://i.imgur.com/FFdF75J.png" width=500>

<!---  ### [BONUS] Digital Wireframes & Mockups  --->

<!---  ### [BONUS] Interactive Prototype --->

## Build Notes

Here's a place for any other notes on the app, it's creation 
process, or what you learned this unit!  

For Milestone 2, include **2+ Videos/GIFs** of the build process here!

## License

Copyright **2024** | **Around Me Dev Team**

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
