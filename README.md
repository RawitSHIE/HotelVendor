# HotelVendor
เป็นการให้บริการสำหรับผู้ที่ต้องการหาโรงแรมเพื่อเข้าพัก
โดยการให้บริการทางด้าน

- การค้นหาโรงแรม
- การจองห้องของโรงแรม

## Busniuess Flow
> Not yet finalize

![BusniuessChart](/ReadmeResource/BusinessChart.png)

## API Structure overview Plan
> Not yet finalize
<center>
  <image src="ReadmeResource/apiStructure.png" alt="api overview"/>
</center>

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
> price may vary overtime dedicate price store foreach booking is needed*

## API Endpoint ที่จำเป็นต่อ Busniess
- API ในการรับผิดชอบการ Filter โรงแรมตาม Attribute การ Search ของลูกค้า
- API ในการรับชอบการตรวจสอบ สถานะของห้องในการจองในช่วงเวลานั้นโดยไม่เกิดการทับกันของการจอง
- API ในการ Update Entity Room โดยยึดจาก Entity Booking
- API ในการ Add และ Update Entity Room ตามโรงแรมต้องการ
- API การสร้าง Notification ของ Booking ให้กับโรงแรม
- API การสร้าง Invoice สำหรับการจอง