#!/usr/bin/python
#-*-coding: utf-8 -*-

import csv

filename = "2015-05-25_rute_midtbyen_310179"

output_file = open(filename + '.dat', 'w+')

# 	0 		1 		2 		   3 	   4 	   5 		6			7
# "id","length","mandatory","start","slutt","fart","enveiskjørt","sperret"

with open(filename + '.csv', 'rb') as csvfile:
	inputted_csv = list(csv.reader(csvfile, delimiter=','))

	number_of_nodes = 0
	number_of_edges = 0
	number_of_arcs = 0
	number_of_required_nodes = 0
	number_of_required_edges = 0
	number_of_required_arcs = 0

	node_IDs = set()

	for row in inputted_csv:
		if row[6] == '':
			number_of_edges += 1
			if row[2] == 't':
				number_of_required_edges += 1
		else:
			number_of_arcs += 1
			if row[2] == 't':
				number_of_required_arcs += 1
		node_IDs.add(row[3])
		node_IDs.add(row[4])
	number_of_nodes = len(node_IDs)

	output_file.write("Name:\t\t"+filename+"\n")
	output_file.write("Optimal value:\t-1"+"\n")
	output_file.write("\#Vehicles:\t-1"+"\n")
	output_file.write("Capacity:\t16000"+"\n")
	output_file.write("Depot Node:\t-1"+"\n")
	output_file.write("\#Nodes:\t\t"+str(number_of_nodes)+"\n")
	output_file.write("\#Edges:\t\t"+str(number_of_edges)+"\n")
	output_file.write("\#Arcs:\t\t"+str(number_of_arcs)+"\n")
	output_file.write("\#Required N:\t"+str(number_of_required_nodes)+"\n")
	output_file.write("\#Required E:\t"+str(number_of_required_edges)+"\n")
	output_file.write("\#Required A:\t"+str(number_of_required_arcs)+"\n")
	output_file.write("\n")
	output_file.write("ReN.\tDEMAND\tS. COST"+"\n")
	output_file.write("\n")

	#process the required edges
	output_file.write("ReE.\tFROM N.\tTO N.\tT. COST\tDEMAND\tS. COST"+"\n")
	for row in inputted_csv:
		if row[6] == '' and row[2] == 't':
			element_id = 'E' + row[0]
			from_node = row[3]
			to_node = row[4]
			if row[5] == '':
				traversal_cost = str(int(float(row[1])))
			else:
				traversal_cost = str(int(float(row[1]) * (150 - int(row[5]))))
			demand = row[1]
			service_cost = row[1]
			string_to_write = element_id + '\t' + from_node + '\t' + to_node + '\t' + traversal_cost + '\t' + demand + '\t' + service_cost
			output_file.write(string_to_write + '\n')
	output_file.write("\n")


	#process the rest of the edges
	output_file.write("EDGE\tFROM N.\tTO N.\tT. COST"+"\n")
	for row in inputted_csv:
		if row[6] == '' and row[2] == 'f':
			element_id = 'NrE' + row[0]
			from_node = row[3]
			to_node = row[4]
			if row[5] == '':
				traversal_cost = str(int(float(row[1])))
			else:
				traversal_cost = str(int(float(row[1]) * (150 - int(row[5]))))
			string_to_write = element_id + '\t' + from_node + '\t' + to_node + '\t' + traversal_cost
			output_file.write(string_to_write + '\n')
	output_file.write("\n")

	# #process the required arcs
	output_file.write("ReA.\tFROM N.\tTO N.\tT. COST\tDEMAND\tS. COST"+"\n")
	for row in inputted_csv:
		if row[6] != '' and row[2] == 't':
			element_id = 'A' + row[0]
			if row[6] == 1:
				from_node = row[3]
				to_node = row[4]
			else:
				from_node = row[4]
				to_node = row[3]
			if row[5] == '':
				traversal_cost = str(int(float(row[1])))
			else:
				traversal_cost = str(int(float(row[1]) * (150 - int(row[5]))))
			demand = row[1]
			service_cost = row[1]
			string_to_write = element_id + '\t' + from_node + '\t' + to_node + '\t' + traversal_cost + '\t' + demand + '\t' + service_cost
			output_file.write(string_to_write + '\n')
	output_file.write("\n")

	# #process the rest of the arcs
	output_file.write("ARC\tFROM N.\tTO N.\tT. COST"+"\n")
	for row in inputted_csv:
		if row[6] != '' and row[2] == 'f':
			element_id = 'NrA' + row[0]
			if row[6] == 1:
				from_node = row[3]
				to_node = row[4]
			else:
				from_node = row[4]
				to_node = row[3]
			if row[5] == '':
				traversal_cost = str(int(float(row[1])))
			else:
				traversal_cost = str(int(float(row[1]) * (150 - int(row[5]))))
			string_to_write = element_id + '\t' + from_node + '\t' + to_node + '\t' + traversal_cost
			output_file.write(string_to_write + '\n')
	output_file.write("\n")
