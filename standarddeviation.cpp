#include<iostream>
#include<vector>
#include <cmath>
using namespace std;

int main() {
    
    vector<float>s1;//Vector create for storing value dynamically.
   float sum = 0.0, mean, variance = 0.0, stddev;
   int n;
   cin>>n;
   int i;
   for( i= 0; i < n; i++)
   {
      float value;
      cin>>value;
      s1.push_back(value);
   }
   for(i = 0; i < n; i++)
   {
       sum=sum+s1[i];
   }
   
      
   mean = sum/5;

   for(i = 0; i < n; i++)
   variance += pow(s1[i] - mean, 2);

   variance=variance/5;//For variance.
    
   stddev = sqrt(variance);//Formula for calculate standard deviation.
    
   cout<<"The give stored values:- ";
    
   for(i = 0; i < n; i++)
      {
          cout<<s1[i]<<" ";
      }
    
      cout<<endl;

      cout<<"Standered deviation:-"<<stddev; //Output.   
}
