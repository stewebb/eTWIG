import cv2
import numpy as np

def cropper(image):

    # Image pre-processings: read, filter and convert to grayscale
    image = cv2.imread(image)
    blurred_img = cv2.pyrMeanShiftFiltering(image, 11, 21)
    grayscale_img = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    
    # Apply the Image Thresholding
    thresh = cv2.threshold(grayscale_img, 0, 255, cv2.THRESH_BINARY_INV + cv2.THRESH_OTSU)[1]

    # Find all counters
    counters = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    counters = counters[0] if len(counters) == 2 else counters[1]
    
    # Stores the cropped images.
    cropped_img = []
    
    # Iterate counters and check whether it's a rectangle
    for c in counters:
        peri = cv2.arcLength(c, True)
        approx = cv2.approxPolyDP(c, 0.015 * peri, True)
        
        # The founded rectangle
        if len(approx) == 4:
            x, y, w, h = cv2.boundingRect(approx)
            #cv2.rectangle(image,(x,y),(x+w,y+h),(36,255,12),2)
            cropped_img.append(image[y: y+h, x: x+w])

            #cv2.imshow('thresh', thresh)
    #cv2.imshow('image', image)
    # cv2.waitKey()
    return cropped_img
        
a = cropper("/home/anakin/Downloads/test.png")
for b in a:
    #print(b)
   cv2.imshow('image', b)
   cv2.waitKey() 