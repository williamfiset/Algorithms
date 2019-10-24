#MAX ALGORITHM
def findMin(list):
	
	minVal = list[0]
	
	for i in range(0, len(list)):
		if list[i] < minVal:
			minVal = list[i]
	return minVal

#MAX ALGORITHM
def findMax(list):
	
	maxVal = list[0]
	
	for i in range(0, len(list)):
		if list[i] > maxVal:
			maxVal = list[i]
	return maxVal	


#TEST	
test_list = [9, 8, 23, 1, 200, 10, 90, 11]
print('The smallest elements is : {}'.format(findMin(test_list)))
print('The largest elements is : {}'.format(findMax(test_list)))
