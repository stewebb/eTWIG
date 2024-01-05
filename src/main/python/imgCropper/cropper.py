import cv2
import numpy as np

RECT_HALF_BOEDER = 10

'''
    Convert a transparent background to white.
    input:
        An image (whose background may transparent)
    returns:
        If the image doesn't has alpha channel, just return itself.
        Else, convert the background to white.
'''

def transparent_to_white(img):
    
    # Only convert then there has an alpha channel.
    if img.shape[2] == 4:
        
        # Split the alpha channel
        bgr, alpha = img[:,:,0:3], img[:,:,3]

        # Create a white background image with the same size as the PNG image
        white_background = np.ones_like(bgr, dtype=np.uint8) * 255

        # Mask the alpha channel to the white background
        foreground = cv2.bitwise_and(bgr, bgr, mask=alpha)
        background = cv2.bitwise_and(white_background, white_background, mask=cv2.bitwise_not(alpha))

        # Add the foreground and background
        return cv2.add(foreground, background)
    
    # Or just return the image itself, no need to convert.
    else:
        return img
    
'''
    Crop a large image to different smaller parts by using rectangle detection.
    To prepare the image, put a black, transparent rectangle outside and cover the components.
    e.g.,
    ----------------
    |                    |
    |    TEXT       |
    |    CIRCLE    |
    |                    |
    ---------------
    input:
        image: The large image after imread.
    returns:
        A list of cropped, small images.
'''

def image_cropper(image):

    # Change the transparent background to white, if required. 
    trans_img = transparent_to_white(image)

    # Image pre-processings: filter and convert to grayscale
    blurred_img = cv2.pyrMeanShiftFiltering(trans_img, 11, 21)
    grayscale_img = cv2.cvtColor(trans_img, cv2.COLOR_BGR2GRAY)
    
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
            
            # Crop image, and consider the border width of the rectangle.
            # Do this on the original image.
            cropped = image[y+RECT_HALF_BOEDER: y+h-RECT_HALF_BOEDER, x+RECT_HALF_BOEDER: x+w-RECT_HALF_BOEDER]
            cropped_img.append(cropped)

    return cropped_img
        
image = cv2.imread("/home/anakin/Downloads/test2.png", cv2.IMREAD_UNCHANGED)

cropped_img = image_cropper(image)
n = 0
for b in cropped_img:
    cv2.imwrite('/home/anakin/Downloads/image'+str(n)+'.png', b)
    n = n + 1
#    #cv2.waitKey() 

