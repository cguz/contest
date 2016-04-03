//============================================================================
// Name        : Solution.cpp
// Author      : Hackerrank
// Version     : 0.1
//============================================================================

#include <iostream>
using namespace std;

long long a[100010];

long long sum(int l, int r) {
    return a[r] - a[l-1];
}

// binary search to find first valid X
// within subarray [l, r]
int get(int l, int r, long long s) {
    int low, high, mid;
    low = l;
    high = r;
    while( low <= high ) {
        mid = ( low + high ) / 2;
        long long x = sum(l, mid);
        if( x == s && (mid == l || sum(l, mid-1) != s )) {
            return mid;
        } else if( x >= s ) {
            high = mid - 1;
        } else {
            low = mid + 1;
        }
    }
    return -1;
}

int solve(int l,int r){
    long long s = sum(l, r);
    if( l != r && s % 2 == 0 ) {
        int ind = get(l, r, s/2);
        if( ind != -1 ) {
            // compute maximum from 2 parts
            return max(solve(l, ind), solve(ind+1, r)) + 1;
        }
    }
    return 0;
}

int main() {
    int t;
    cin>>t;
    while (t--){
        int n;
        cin>>n;
        for (int i=1;i<=n;i++) {
            cin>>a[i];
            a[i]+=a[i-1];
        }
        cout<<solve(1,n)<<endl;
    }
    return 0;
}
