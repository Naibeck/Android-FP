# Android FP App

## Functional programming
I decided to go with functional programming due to the the benefit to reuse each function, following the paradigm it ended with a few classes most of them applicable to the Android framework and the service API from retrofit.
Functional programming is currently one of my study subjects so it also became a personal reason to develop this project by following that paradigm.

## Architecture
In the architecture I decided to write files that only has function on it, but are representing  the way of how classes and objects should interact with each other.
Due to the simplicity of the project there are some features that are not implemented, for example checking the CachePolicy classes the project is allowed to implement a different policy being the only one Network.
The presentation layer is using MVP again for the simplicity of implementing it.

## Mothers
In both testing projects there exists a concept of `mother` that will allow us to avoid code duplication for the mock server on different tests. The idea behind mothers is to provide same implementation with multiple params when calling a function. Again this follows functional by using extension functions.

## Feature
### Paginated scroll
The api response includes over 4K of items, which load all the pictures at once, the project could use the repository responsibility to take count and load only a small portion of the images. That could lead the api to introduce a pagination mechanism and reduce the size of the response improving its times.

### In memory cache
Using the benefits of the PolicyCache sealed class we can add a new policy that will lead the user to consume the resource the first time over the network and then stored the response locally using a DatabaseCache by using Room. This could improve the times of response if the users connection is poor.

### Content sharing
Sharing images is a great feature that most of the users wants, having the opportunity to do so could improve the users retention and acquisition over time. And if we follow the same architecture we would start by creating a new use case to share its content.

There are other technical requirements that could be discussed to be included in the project for example using the PageObject pattern for the UI tests.

## Most important libraries
* Arrow
* Retrofit
* MockWebServer
* Hiroaki

