# HotelVendor
เป็นการให้บริการสำหรับผู้ที่ต้องการหาโรงแรมเพื่อเข้าพัก
โดยการให้บริการทางด้าน

- การค้นหาโรงแรม
- การจองห้องของโรงแรม

## Busniuess Flow
![BusniuessChart](/ReadmeResource/BusinessChart.png)

## โครงสร้างของระบบ
![ProjectStructure]()

## Json ของ Entity มีดังนี้
### Hotel
```json
{
  "hotelId" : "number",
  "hotelName" : "string",
  "location" : {
    "Country" : "string",
    "provinceState" : "string",
    "district": "string",
    "Street": "string",
    "additionalDetail" : "string",
    },
  "isAvailible" : "boolean",
  "contactInfo" : {
    "tel" : [
      "string"
    ],
    "email" : [
      "string"
    ]
  }
}
```

### Room
```json
{
  "hotelId" : "number",
  "room": {
    "isAvailible" : "boolean",
    "availible" : "number",
    "roomTypeAvailible" : "number",
    "roomType" : [
      {
        "roomTypeName" : "string",
        "availableRoom" : [
          {
            "roomNumber" : "string",
            "price" : "string",
            "bookedDate" : [
              {
                "bookingId" : "string",
                "bookingdate" : "date"
              }
            ]
          }
        ]
      }
    ]
  }
}
```

### User
```json
{
  "userId" : "number",
  "name" : {
    "firstName" : "string",
    "middleName" : "string",
    "lastName" : "string",
  },
  "contactInfo" : {
    "tel" : "string",
    "email" : "string",
  },
}
```

### Booking
```json
{
  "bookingId" : "number",
  "userId" : "number",
  "hotelId" : "number",
  "BookingDetail" : {
    "bookingStatus" : "string",
    "bookingCreateDate" : "date-time",
    "checkInDate" : "date",
    "checkOutDate" : "date"
  },
  "bookedRoom" : [
    {
      "roomNumber" : "string",
      "price" : "number"
    }
  ],
  "totalPrice" : "number"
}
```

## API Endpoint ที่จำเป็นต่อ Busniess