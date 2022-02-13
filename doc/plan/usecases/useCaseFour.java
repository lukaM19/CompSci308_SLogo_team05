//for now we think handling UI Button interactions in View(except Run button), using listeners is acceptable.
try{
  turtle.setPenColor(newColor);
    }
catch{InvalidColorException E}{
  turtle.setPenColor(DEFAULT_COLOR)
    displayException("Invalid color, using default color instead \n "+E.getMessage())
    }
