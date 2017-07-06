#include <iostream>
#include <algorithm>
#include <set>
#include <vector>
#include <string>
using namespace std;



bool consequent_number(string &lhs, string &rhs) {
	size_t i = 0, j = 0;
	bool ret = true;
	// if rhs[0] == lhs[0]+1 then 
	// following characters in rhs must be 0 and
	// in lhs must be 9
	if (rhs.size() > 1) {
		if (rhs[0] == lhs[0] + 1) {
			++i, ++j;
			while (i < lhs.size() && j < rhs.size()) {
				if (lhs[i] != '9' || rhs[j] != '0') {
					ret = false;
					break;
				}
				++i;
				++j;
			}
		}
		else {
			ret = false;
		}
	}
	else {
		ret = (rhs[0] == lhs[0] + 1);
	}
	return ret;
}

bool consequent(string &lhs, string &rhs) {
	size_t i = 0, j = 0;
	bool ret = false;
	while (lhs[i] == rhs[j]) {
		if (!isdigit(lhs[i])) {
			++i;
		}
		else {
			break;
		}
		if (!isdigit(lhs[j])) {
			++j;
		}
		else {
			break;
		}
	}
	if (i != j) {
		ret = false;
	}
	else {
		// alphabets match
		//check for the number
		if ((lhs.size() - i) != (rhs.size() - j)) {
			ret = false;
		}
		else {
			// number size match
			// check if they are consecutive
			string lhs_str = lhs.substr(i);
			string rhs_str = rhs.substr(j);
			//cout << lhs_str << " " << rhs_str << endl;
			i = 0, j = 0;
			while (i < lhs_str.size() && j < rhs_str.size() &&
				lhs_str[i] == rhs_str[j]) {
				++i;
				++j;
			}
			if (i == lhs_str.size()) {
				return true;
			}
			string lhs_sstr = lhs_str.substr(i);
			string rhs_sstr = rhs_str.substr(j);
			//cout << "non matching str\n";
			//cout << lhs_sstr << " " << rhs_sstr << endl;
			ret = consequent_number(lhs_sstr, rhs_sstr);
		}
	}
	return ret;
}

string find_range(size_t &cur, set<string> &s, size_t &j) {
	size_t temp_cur = cur;
	size_t temp_j = j;
	set<string>::iterator lhs = s.begin();
	set<string>::iterator rhs = s.begin();
	std::advance(lhs, cur);
	std::advance(rhs, j);
	for (lhs, rhs; lhs != s.end() && rhs != s.end();) {
		string lhs_str = *lhs;
		string rhs_str = *rhs;
		if (lhs_str.length() != rhs_str.length()) {
			break;
		}
		if (consequent(lhs_str, rhs_str) && j < s.size()) {
			cur = j;
			++j;
			lhs = s.begin();
			rhs = s.begin();
			std::advance(lhs, cur);
			std::advance(rhs, j);
		}
		else {
			break;
		}
	}
	lhs = s.begin();
	rhs = s.begin();
	std::advance(lhs, cur);
	std::string ret = *lhs;
	if (temp_cur != j - 1) {
		std::advance(rhs, temp_cur);
		ret = *rhs + " - " + *lhs;
	}
	cur = temp_cur;
	return ret;
}

void tokenize(vector<string> &output, string &str, string delimiter) {
	// Skip delimiters at beginning.
	string::size_type lastPos = str.find_first_not_of(delimiter, 0);
	// Find first "non-delimiter".
	string::size_type pos = str.find_first_of(delimiter, lastPos);

	while (string::npos != pos || string::npos != lastPos)
	{
		// Found a token, add it to the vector.
		output.push_back(str.substr(lastPos, pos - lastPos));
		// Skip delimiters.  Note the "not_of"
		lastPos = str.find_first_not_of(delimiter, pos);
		// Find next "non-delimiter"
		pos = str.find_first_of(delimiter, lastPos);
	}
}

void removeWhiteSpace(string &input) {
	input.erase(remove(input.begin(), input.end(), ' '), input.end());
	input.erase(remove(input.begin(), input.end(), '\t'), input.end());
	input.erase(remove(input.begin(), input.end(), '\n'), input.end());
	input.erase(remove(input.begin(), input.end(), '\r'), input.end());
}

void accession_range(set<string> &token) {
	vector<string> vec;
	size_t j = 1;
	string str;
	for (size_t i = 0; i < token.size() /*&& j < token.size()*/;) {
		str = find_range(i, token, j);
		vec.push_back(str);
		str.clear();
		i = j;
		++j;
	}
	for (vector<string>::iterator it = vec.begin(); it != vec.end(); ++it) {
		cout << *it << endl;
	}
}

int main() {
	try {
		string input;
		vector<string> vec;
		set<string> token;
		while (true) {
			cout << "Input comma seprated string" << endl;
			getline(cin, input);
			if (input.compare("quit") == 0) {
				break;
			}
			removeWhiteSpace(input);
			tokenize(vec, input, ",");
			token.insert(vec.begin(), vec.end());
			accession_range(token);
			token.erase(token.begin(), token.end());
			vec.erase(vec.begin(), vec.end());
			input.clear();
		}
	}
	catch (exception const &e) {
		cout << e.what() << endl;
	}
	return 0;
}
