# Supply / Sell Side platform

## Introduction

Simplified system for automatic sale of various electronic goods (e.g. promotional codes, game keys) in real time bidding.

## Terminology

__SSP__: Supply / Sell Side platform.

__DSP__: Demand Side Platform.

__RTB__: Real-Time Bidding.

__Goods Supplier__: Supply __Product__ to sold. #fixme extend termin

__Product__: An virtual product that provides value for bidders (promocode, game access key, etc). __Product__ has __Product Description__ and __Product Value__. 

__Product Description__: __Product__ parameters that may interest the customer.

__Product Value__: __Product__ parameters that are of commercial value (example, promocode value).

__Product Set__: A set of __Product__, which is sold via SSP by an abstract supplier. All products is comparable by quality in the set. __Products__ can be not unique in the set. Goods has attibutes: goods supplier code, product code, product description and product.
`#fixme goods is quantum of goods flow from supplier.`

  > _Set_ in __Product Set__ is not equal Math Set.

__Auction__: Mechanism for distributing goods between auction members. Auction is closed and one-round.

__Lot__: __Product__ sold at auction. Lot and goods set element has relations as one-to-one (One lot contains one set product). Each lot is unique on SSP.

__Lots__: Set of lots, which is sold at one round of auction.

__Auction Invite__: DSP invitation to auction. Its contains lots and auction algorithm (VCG, GSP, High stakes, etc).

__Bid Request__: DSP Response on invitation. Contains a list of lots and bids on them.

__Auction Result__: Winner and payment amount for each lot.

__Win Notification__: If the DSP wins in round of auction then it receives a notification with the lots won and payment amount.

__Auction History__: Statistics about auction result. Contains member's bids, winner, lots with payment amount, etc.

__Auction Strategy__: Winner selection and payment amount algorithm – VCG, GSP, etc.

__Bidder__:  DSP who bids on lots in a concrete round of the auction.

__Bidder Candidate__: Potential bidder who may be interested in the type of __Product__ to be sold 

__Auction Manager__: Mechanism with a high level logic of auction management. Sets __Auction Strategy__, __Bidder Candidates__ list and __Bid Request__ waiting timeout.

__Waiting Bid Timeout__: Maximum bid waiting time.

## Algorithms
Picture 1 – Sequence diagram of SSP communication with external systems


### General

1. Get __Product Set__ from __GoodsSupplier__.
2. Create __Lot__ for each product on __Product__. __Lot__ contain generated lot identifier and product.
3. Form __Lots__ from __Lot__, uniting them into one list.
4. Send over the lots to the __Auction Manager__ and get __Auction Strategy__ (VCG, GSP, etc), 
__Bidder Candidates__ list, __Waiting Response Timeout__.
5. Hold auction:
    1. Send __Auction Invite__ to __Bidder Candidates__ and wait response with __Waiting Bid Timeout__.
    2. Consumes bid request from __Bidder__ and apply __Auction Strategy__.
    3. Save __Auction Result__ to __Auction History__ store.
    4. Send __Win Notification__ to winner.
    

## Architecture

![pic1](https://github.com/eutkin/diagrams/blob/master/GeneralDataFlow.svg)

### Components

#### Goods Supplier EntryPoint

Entrypoint for goods supplier. Example, REST API Endpoint.

Consumes set of product and enrich each product addititional info: product type, supplier code.

#### Goods Supplier

Supplies sets of goods (__Product Set__) for sale at auction. The goods are delivered to the system by push model in the form of events, unevenly distributed over time. 
![goods-provider-flow](https://github.com/eutkin/diagrams/blob/master/GoodsSupplierAsFlow.svg)

All __Product__ in the set must be comparable by quality. 

> Example, __Product Set__ contains game access keys. The keys can be comparable by game price.

#### Lots Provider

Form __Lots__ from __Product Set__. __Lot__ is wrapper for __Product__. Each __Lot__ has unique identifies (UUID) and is comparable by __Product__.

#### DSP Store

Store information about DSP:

- DSP unique identifier
- DSP auction invite endpoint
- DSP win endpoint
- DSP list of products you would like to see

Must implements functions:
- save DSP
- find DSP by productType

#### Auction Strategy

Auction Alghorithm: VCG, GSP, etc.

Select winners from bidders and determine payment amount.

#### Auction Manager

#### DSP Communicator

#### Auction History Store

#### Auction Hook

#### Auctions
