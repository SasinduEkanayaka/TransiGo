# TransiGo Drawable Icons

## Material3 Theme with Teal Primary Colors

TransiGo uses a complete Material3 design system with:
- **Primary Color**: Teal (#00695C)
- **Secondary Color**: Light Teal (#4DB6AC) 
- **Rounded Shapes**: 8dp (small), 16dp (medium), 24dp (large)
- **Dark Theme Support**: Automatic switching based on system settings

## Available Custom Icons

### Transport & Branding Icons
1. **ic_banner_transport.xml** - Main banner with city skyline and transport icons
   - Used in: HomeScreen welcome banner
   - Features: City silhouette, bus, car, and plane icons
   - Suggested Freepik alternatives:
     - https://www.freepik.com/free-vector/city-transport-bus-travel-banner_6978234.htm
     - https://www.freepik.com/free-vector/public-transport-banner-design_8745123.htm  
     - https://www.freepik.com/free-vector/travel-banner-template_15678901.htm

2. **ic_auth_banner.xml** - Authentication banner with security theme
   - Used in: Login/Register screens
   - Features: Mobile device, security shield, key icons
   - Suggested Freepik alternatives:
     - https://www.freepik.com/free-vector/mobile-login-concept-illustration_8765432.htm
     - https://www.freepik.com/free-vector/secure-login-banner-template_12345678.htm
     - https://www.freepik.com/free-vector/authentication-abstract-concept_9876543.htm

### Location Icons
3. **ic_pickup_location.xml** - Pickup location marker
   - Used in: BookingFormScreen pickup field
   - Features: GPS pin with center dot
   - Color: Primary teal
   - Suggested Freepik alternatives:
     - https://www.freepik.com/free-vector/location-pin-icon-set_11234567.htm
     - https://www.freepik.com/free-vector/map-marker-icons-collection_9876543.htm
     - https://www.freepik.com/free-vector/gps-navigation-pin-icon_7654321.htm

4. **ic_dropoff_location.xml** - Drop-off destination flag
   - Used in: BookingFormScreen drop-off field
   - Features: Flag with pole design
   - Color: Secondary teal
   - Suggested Freepik alternatives:
     - https://www.freepik.com/free-vector/destination-flag-icon-set_11234567.htm
     - https://www.freepik.com/free-vector/finish-line-flag-icons_9876543.htm
     - https://www.freepik.com/free-vector/goal-destination-pin-marker_7654321.htm

### User Interface Icons
5. **ic_avatar_placeholder.xml** - User profile avatar
   - Used in: ProfileScreen user avatar
   - Features: Person silhouette in circle
   - Size: 120dp circular design
   - Suggested Freepik alternatives:
     - https://www.freepik.com/free-vector/user-avatar-profile-icon-set_11234567.htm
     - https://www.freepik.com/free-vector/person-circle-avatar-collection_9876543.htm
     - https://www.freepik.com/free-vector/profile-user-icon-vector-set_7654321.htm

6. **ic_booking_ticket.xml** - Booking/ticket icon
   - Used in: HomeScreen "Book a Ride" button
   - Features: Perforated ticket design
   - Suggested Freepik alternatives:
     - https://www.freepik.com/free-vector/ticket-booking-icons-collection_11234567.htm
     - https://www.freepik.com/free-vector/transport-ticket-template-set_9876543.htm
     - https://www.freepik.com/free-vector/travel-booking-confirmation-icon_7654321.htm

7. **ic_route.xml** - Navigation/route icon
   - Features: Dotted path with directional arrow
   - Usage: Can be used for route planning features
   - Suggested Freepik alternatives:
     - https://www.freepik.com/free-vector/navigation-route-icons-set_11234567.htm
     - https://www.freepik.com/free-vector/gps-navigation-path-marker_9876543.htm
     - https://www.freepik.com/free-vector/route-direction-arrow-icons_7654321.htm

## Accessibility Features

All custom icons include:
- **Content Descriptions**: Meaningful descriptions for screen readers
- **Minimum Touch Targets**: 48dp minimum for interactive elements
- **Proper Color Contrast**: Follows WCAG guidelines
- **Scalable Design**: Vector format for all screen densities
- **Tint Support**: Icons adapt to theme colors automatically

## Theme Integration

Icons are designed to work seamlessly with the Material3 theme:
- **Primary Tint**: Used for main action icons
- **Secondary Tint**: Used for supporting action icons  
- **On-Surface Tint**: Used for neutral icons
- **Automatic Dark Mode**: Icons adapt to system theme
3. Place them in the drawable folder
4. Update the icon references in the following files:
   - `BookingFormScreen.kt` (lines with TODO comments)
   - `BookingHistoryScreen.kt` (lines with TODO comments)

### Current Temporary Icons
- MyLocation (Material Icons) - Currently used for pickup
- LocationOn (Material Icons) - Currently used for drop

Replace the Material Icons imports and Icon components with the custom Freepik icons once added.
