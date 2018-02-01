# Java Apportionment Example

#### Your have a purchase with the following

```
{ 
    "nominalValue": "10.0",
    "productPriceList": [
        {
           "id": "1",
           "quantity": "3",
           "price": "3.34"
        }
    ]
}
```
#### Then: 
```
double expected = 10.0;
double realTotal = 3.34 * 3;
assertEquals(expected, realTotal)
```
`OBVIOUSLY YOU WILL GET AN ASSERTION ERROR DOING THIS`

### So take a look about this implementation!
[Usage example](/src/test/java/br/com/roggen/apportionment/ApportionmentServiceTest.java)
