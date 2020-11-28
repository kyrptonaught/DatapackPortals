
# Datapack Portals
Create custom portals using [CustomPortalApi](https://github.com/kyrptonaught/customportalapi) with datapacks.

Inside your datapack create a "portals" folder, this is where you'll put all your custom portals. There is no set limit of allowed portals, as long as there aren't any duplicate frame blocks.

Here is an example file: 
stoneendportal.json

     {  
	    "block": "minecraft:stone",  
	    "dim": "minecraft:the_end",  
	    "r": 66,  
	    "g": 135,  
	    "b": 245,
     }

 - block: the block used for the frame. 
 - dim: the dim id to travel to
 -  r, g, b: the
   color to tint the portal

There are also some optional arguments

 - ignitionBlock: the source for ignition the portal, currently only "fire" and "water" are supported with more support coming eventually(tm). Fire being flint and steel, water being water bucket aether style
 - returnDim: the dimmension to return to, allows for specifying a location other than the overworld to return to

That is all! Portals will function exactly like vanilla nether portals do
![portals and stuff](https://raw.githubusercontent.com/kyrptonaught/customportalapi/main/images/2020-11-15_17.06.44.png)

