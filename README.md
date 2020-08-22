# Supply / Sell Side platform

## Description

Simplified system for automatic sale of various electronic goods (e.g. promotional codes, game keys) in real time bidding.

## Architecture

![pic1](https://github.com/eutkin/diagrams/blob/master/GeneralDataFlow.svg)


### Components

#### Goods Supplier

Supplies sets of goods (__Product Set__) for sale at auction. The goods are delivered to the system by push model in the form of events, unevenly distributed over time. 

![goods-provider-flow](https://github.com/eutkin/diagrams/blob/master/GoodsSupplierAsFlow.svg)

#### Lots Provider

#### DSP Store

#### Auction Strategy

#### Auction Manager

#### DSP Communicator

#### Auction History Store

#### Auction Hook

#### Auctions

