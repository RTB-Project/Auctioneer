# Supply / Sell Side platform

## Description

Simplified system for automatic sale of various electronic goods (e.g. promotional codes, game keys) in real time bidding.

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

