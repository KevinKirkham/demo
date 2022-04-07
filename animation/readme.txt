This is an animation project that I originally worked as part of the ITEC 324 class at Radford University. The goal was to display 1-3 shapes in a JPanel and have them animate across that panel, sliding in a linear fashion along some path. They were to wrap to the opposite side of the screen when they reached the edge of the screen. The three buttons at the top were originally meant to control the animation, though how the shapes are animated on the screen has since changed rendering the Show button useless.

The abbreviated description of how it works: 

Each Shape has an Animation object, which handles all things associated with the sprites in the shape's animation, and a Path object which does the math for calculating and maintaining the Shape's current position. The details of how each of these classes performs its job are written in the JavaDocs in each class. 

To instantiate a specific Shape, such as a Rocket, Bird, or UFO, the X-Y coordinates at which you want the Shape to be spawned must be supplied in the call to the constructor as well as the delta Y and delta X so that a linear path including the point that was specified in the call to the constructor can be made. A Shape's path data is calculated on its creation - it is not calculated on the fly. The entirety of the Shape's path will be known just after it is created.

Wrapping


